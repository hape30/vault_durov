import { Container } from '@chakra-ui/react'
import { FC, ReactNode } from 'react'
import Header from '../../components/Header/Header'
import Footer from '../../components/Footer/Footer'

interface FluidLayoutPropsI {
	title?: string
	children: ReactNode
}

const FluidLayout: FC<FluidLayoutPropsI> = ({ title, children }) => {
	return (
		<>
			<Header title={title} />
			<main>
				<Container maxW='98vw'>{children}</Container>
			</main>
			<Footer />
		</>
	)
}

export default FluidLayout
