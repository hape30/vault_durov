export type InputPropsT = {
	type: 'password' | 'email' | 'text'
	classList?: string
	value: string
	onInput: FormEventHandler<HTMLInputElement>
}
