import { Input } from '@chakra-ui/react'
import { FC, useState } from 'react'

import styles from './EntitledInput.module.scss'

interface EntitledInputPropsI {
	label: string
	type: 'password' | 'email' | 'text'
	classList?: string
}

const EntitledInput: FC<EntitledInputPropsI> = ({ label, type, classList }) => {
	const [value, setValue] = useState('')

	return (
		<div className={styles['entitled-input-container']}>
			<Input
				onInput={(e) => setValue((e.target as HTMLInputElement)?.value)}
				className={
					styles['entitled-input'] +
					' ' +
					(value.length > 0 ? styles['entitled-input-filled'] : '') +
					' ' +
					(classList || '')
				}
				type={type}
			/>
			<span className={styles['entitled-input-text']}>{label}</span>
		</div>
	)
}

export default EntitledInput
