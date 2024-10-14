import { FC, useState } from 'react'
import LoginForm from '../../components/LoginForm/LoginForm'
import { Center, Text } from '@chakra-ui/react'

import { constants } from '../../constants/constants'
import Title from '../../ui/Title/Title'
import RegForm from '../../components/RegForm/RegForm'

import styles from './AuthPage.module.scss'

interface AuthPagePropsI {}

const AuthPage: FC<AuthPagePropsI> = () => {
	const [activeForm, setActiveForm] = useState<'login' | 'reg'>('login')
	const isLoginFormActive = activeForm === 'login'

	return (
		<div className={styles['auth']}>
			<Center className={styles['auth-content']} width='500px'>
				<Title text={isLoginFormActive ? 'Вход' : 'Регистрация'} classlist={styles['auth-title']} />
				{isLoginFormActive ? <LoginForm /> : <RegForm />}
				<Text color={constants.primaryColorBlue} fontSize='16px'>
					<a
						href='/'
						className='link'
						onClick={(e) => {
							e.preventDefault()
							setActiveForm(isLoginFormActive ? 'reg' : 'login')
						}}>
						{isLoginFormActive ? 'Нет аккаунта? Зарегистрироваться' : 'Есть аккаунт? Войти'}
					</a>
				</Text>
			</Center>
		</div>
	)
}

export default AuthPage
