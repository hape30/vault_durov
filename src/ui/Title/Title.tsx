import { Heading } from '@chakra-ui/react'
import { ElementType, FC } from 'react'

interface TitlePropsI {
	text: string
	tag?: ElementType
	classlist?: string
}

const Title: FC<TitlePropsI> = ({ tag, text, classlist }) => {
	return (
		<Heading as={tag || 'h1'} className={classlist || ''}>
			{text}
		</Heading>
	)
}

export default Title
