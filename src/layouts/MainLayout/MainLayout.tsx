import { Container } from '@chakra-ui/react'
import { FC, ReactNode } from 'react'
import Title from '../../ui/Title/Title'

import styles from './MainLayout.module.scss'
import Header from '../../components/Header/Header'
import Footer from '../../components/Footer/Footer'

interface MainLayoutPropsI {
	children: ReactNode
}

const MainLayout: FC<MainLayoutPropsI> = ({ children }) => {
	return (
		<>
			<Header />
			<main>
				<Container maxW='1280px'>{children}</Container>
			</main>
			<Footer />
		</>
	)
}

export default MainLayout
