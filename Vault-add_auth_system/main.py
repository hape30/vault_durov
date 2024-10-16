from fastapi import FastAPI, UploadFile, File, HTTPException
from pydantic import BaseModel
from cryptography.hazmat.primitives.asymmetric import rsa, padding
from cryptography.hazmat.primitives import serialization, hashes
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend
import os

app = FastAPI()

# Генерация пары ключей RSA
private_key = rsa.generate_private_key(
    public_exponent=65537,
    key_size=2048,
    backend=default_backend()
)
public_key = private_key.public_key()

# Функции для шифрования и дешифрования файлов

def encrypt_aes_gcm(file_data: bytes, aes_key: bytes, nonce: bytes):
    cipher = Cipher(algorithms.AES(aes_key), modes.GCM(nonce), backend=default_backend())
    encryptor = cipher.encryptor()
    ciphertext = encryptor.update(file_data) + encryptor.finalize()
    return ciphertext, encryptor.tag

def decrypt_aes_gcm(ciphertext: bytes, tag: bytes, aes_key: bytes, nonce: bytes):
    cipher = Cipher(algorithms.AES(aes_key), modes.GCM(nonce, tag), backend=default_backend())
    decryptor = cipher.decryptor()
    return decryptor.update(ciphertext) + decryptor.finalize()

def encrypt_rsa(aes_key: bytes, public_key):
    encrypted_key = public_key.encrypt(
        aes_key,
        padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None
        )
    )
    return encrypted_key

def decrypt_rsa(encrypted_key: bytes, private_key):
    decrypted_key = private_key.decrypt(
        encrypted_key,
        padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None
        )
    )
    return decrypted_key

# API для шифрования файла
@app.post("/encrypt-file/")
async def encrypt_file(file: UploadFile = File(...)):
    try:
        # Генерация ключа и nonce для AES
        aes_key = os.urandom(32)  # 256-битный ключ AES
        nonce = os.urandom(12)  # 96-битный nonce

        # Чтение содержимого файла
        file_data = await file.read()

        # Шифрование файла с использованием AES
        ciphertext, tag = encrypt_aes_gcm(file_data, aes_key, nonce)

        # Шифрование ключа AES с использованием RSA
        encrypted_aes_key = encrypt_rsa(aes_key, public_key)

        # Сохраняем зашифрованные данные в файл
        encrypted_file_path = f"encrypted_{file.filename}"
        with open(encrypted_file_path, "wb") as encrypted_file:
            encrypted_file.write(ciphertext)

        # Возвращаем путь к зашифрованному файлу и зашифрованный ключ AES
        return {
            "message": "File encrypted successfully",
            "encrypted_file_path": encrypted_file_path,
            "nonce": nonce.hex(),
            "tag": tag.hex(),
            "encrypted_key": encrypted_aes_key.hex()
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# Модель для расшифровки
class DecryptRequest(BaseModel):
    encrypted_file_path: str
    nonce: str
    tag: str
    encrypted_key: str

# API для расшифровки файла
@app.post("/decrypt-file/")
async def decrypt_file(request: DecryptRequest):
    try:
        # Чтение зашифрованного файла
        with open(request.encrypted_file_path, "rb") as encrypted_file:
            ciphertext = encrypted_file.read()

        # Преобразуем строковые параметры в байты
        nonce = bytes.fromhex(request.nonce)
        tag = bytes.fromhex(request.tag)
        encrypted_key = bytes.fromhex(request.encrypted_key)

        # Расшифровка ключа AES с использованием RSA
        aes_key = decrypt_rsa(encrypted_key, private_key)

        # Расшифровка файла с использованием AES
        decrypted_file_data = decrypt_aes_gcm(ciphertext, tag, aes_key, nonce)

        # Сохранение расшифрованного файла
        decrypted_file_path = f"decrypted_{os.path.basename(request.encrypted_file_path)}"
        with open(decrypted_file_path, "wb") as decrypted_file:
            decrypted_file.write(decrypted_file_data)

        return {
            "message": "File decrypted successfully",
            "decrypted_file_path": decrypted_file_path
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
