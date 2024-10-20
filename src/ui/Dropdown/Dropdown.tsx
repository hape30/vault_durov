import { FC, useState } from 'react'
import styles from './Dropdown.module.scss'

interface DropdownPropsI {
	items: string[]
	classList?: string
	label: string
}

const Dropdown: FC<DropdownPropsI> = ({ items, classList, label }) => {
	const [className, setClassName] = useState<string>(styles['dropdown'] + (classList ? ` ${classList}` : ''))

	const mouseEnterHandler = () => {
		setClassName(styles['dropdown'] + ' ' + styles['dropdown_active'] + (classList ? ` ${classList}` : ''))
	}

	const mouseOutHandler = () => {
		setClassName(styles['dropdown'] + ' ' + (classList ? ` ${classList}` : ''))
	}

	return (
		<div onMouseEnter={mouseEnterHandler} onMouseLeave={mouseOutHandler} className={className}>
			{label}
			{items.map((item) => {
				return <div className={styles['dropdown__item']}>{item}</div>
			})}
		</div>
	)
}

export default Dropdown
