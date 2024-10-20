import { Input } from '@chakra-ui/react'
import { FC } from 'react'

import styles from './EntitledInput.module.scss'
import { InputPropsT } from '../inputs'

interface EntitledInputPropsI extends InputPropsT {
	label: string
}

const EntitledInput: FC<EntitledInputPropsI> = ({ label, type, classList, onInput, value }) => {
	return (
		<div className={styles['entitled-input-container']}>
			<Input
				onInput={onInput}
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
