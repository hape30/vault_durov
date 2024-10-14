import { Button } from '@chakra-ui/react'
import { FC } from 'react'
import { ButtonPropsT } from '../Buttons'

import { constants } from '../../../constants/constants'

const DefaultButton: FC<ButtonPropsT> = ({ text }) => {
	return (
		<Button bgColor={constants.primaryColorBlue} color='white' className='btn'>
			{text}
		</Button>
	)
}

export default DefaultButton
