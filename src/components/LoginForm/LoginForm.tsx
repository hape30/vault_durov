import { FC } from 'react'
import EntitledInput from '../../ui/Inputs/EntitledInput/EntitledInput'
import DefaultButton from '../../ui/Buttons/DefaultButton/DefaultButton'

import styles from './LoginForm.module.scss'
import { useInput } from '../../hooks/useInput'

interface LoginFormPropsI {}

const LoginForm: FC<LoginFormPropsI> = () => {
	const email = useInput('')
	const password = useInput('')

	return (
		<form className='form auth-form' id='login-form' method='GET'>
			<EntitledInput {...email} label='Email' classList={styles.input} type='email' />
			<EntitledInput {...password} label='Password' classList={styles.input} type='password' />
			<DefaultButton type='submit' text='Войти' />
		</form>
	)
}

export default LoginForm
