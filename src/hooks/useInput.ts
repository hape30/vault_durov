import { FormEventHandler, useState } from 'react'

export const useInput = (initialValue: string) => {
	const [value, setValue] = useState(initialValue)

	const onInput: FormEventHandler<HTMLInputElement> = (e) => {
		setValue((e.target as HTMLInputElement).value)
	}

	return { value, onInput }
}
