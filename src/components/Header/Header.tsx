import { Button, Container } from '@chakra-ui/react'
import { FC } from 'react'
import Title from '../../ui/Title/Title'

import styles from './Header.module.scss'
import { LogIn } from 'lucide-react'
import { constants } from '../../constants/constants'

interface HeaderPropsI {
	title?: string
}

const Header: FC<HeaderPropsI> = ({ title }) => {
	return (
		<header style={{ backgroundColor: constants.primaryColorBlue }} className={styles.header}>
			<Container className={styles['header-container']} maxWidth='98vw'>
				<Title text={title || ''} />
				<nav className='header-navigation'>
					<ul className={styles['header-navigation-list']}>
						<li className='leader-navigation-item'>
							<a href='#' className='header-navigation-link'></a>
						</li>
					</ul>
				</nav>
				<Button className={styles['header-button']} variant='solid'>
					Войти <LogIn size={18} />
				</Button>
			</Container>
		</header>
	)
}

export default Header
