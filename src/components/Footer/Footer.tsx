import { Container } from '@chakra-ui/react'
import { FC } from 'react'
import { constants } from '../../constants/constants'

interface FooterPropsI {}

const Footer: FC<FooterPropsI> = () => {
	return (
		<footer style={{ backgroundColor: constants.primaryColorBlue }} className='footer'>
			<Container maxW='98vw'></Container>
		</footer>
	)
}

export default Footer
