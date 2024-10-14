import { FC } from 'react'
import EntitledInput from '../../ui/Inputs/EntitledInput/EntitledInput'
import DefaultButton from '../../ui/Buttons/DefaultButton/DefaultButton'

import styles from './RegForm.module.scss'

interface RegFormPropsI {}

const RegForm: FC<RegFormPropsI> = () => {
	return (
		<form className='form auth-form' id='reg-form' method='POST'>
			<EntitledInput label='Email' classList={styles.input} type='email' />
			<EntitledInput label='Password' classList={styles.input} type='password' />
			<EntitledInput label='Confirm password' classList={styles.input} type='password' />
			<DefaultButton text='Зарегистрироваться' />
			<input type='submit' value='Зарегистрироваться' />
		</form>
	)
}

export default RegForm
