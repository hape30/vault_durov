import { FC } from 'react'
import EntitledInput from '../../ui/Inputs/EntitledInput/EntitledInput'
import DefaultButton from '../../ui/Buttons/DefaultButton/DefaultButton'

import styles from './LoginForm.module.scss'

interface LoginFormPropsI {}

const LoginForm: FC<LoginFormPropsI> = () => {
	return (
		<form className='form auth-form' id='login-form' method='GET'>
			<EntitledInput label='Email' classList={styles.input} type='email' />
			<EntitledInput label='Password' classList={styles.input} type='password' />
			<DefaultButton text='Войти' />
		</form>
	)
}

export default LoginForm
