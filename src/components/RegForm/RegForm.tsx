import { FC, FormEvent, useState } from 'react'
import EntitledInput from '../../ui/Inputs/EntitledInput/EntitledInput'
import DefaultButton from '../../ui/Buttons/DefaultButton/DefaultButton'

import styles from './RegForm.module.scss'
import { useInput } from '../../hooks/useInput'
import { postData } from '../../services/postData'

interface RegFormPropsI {}

const RegForm: FC<RegFormPropsI> = () => {
	const email = useInput('')
	const password = useInput('')
	const confirmedPassword = useInput('')
	const [error, setError] = useState('')

	const submitHandler = async (e: FormEvent) => {
		e.preventDefault()

		if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value)) {
			setError('Некорректный email')
			return
		}

		if (password.value !== confirmedPassword.value) {
			setError('Пароли не совпадают')
			return
		}

		setError('')
		await postData('/auth/signup', {
			email: email.value,
			password: password.value,
		})
		
	}

	return (
		<>
			<form onSubmit={submitHandler} className='form auth-form' id='reg-form' method='POST'>
				<EntitledInput {...email} label='Email' classList={styles.input} type='email' />
				<EntitledInput {...password} label='Password' classList={styles.input} type='password' />
				<EntitledInput
					{...confirmedPassword}
					label='Confirm password'
					classList={styles.input}
					type='password'
				/>
				<DefaultButton type='submit' text='Зарегистрироваться' />
			</form>
			{error}
		</>
	)
}

export default RegForm
