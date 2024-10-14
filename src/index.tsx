import React from 'react'
import ReactDOM from 'react-dom/client'
import { ChakraProvider, GlobalStyle } from '@chakra-ui/react'
import HomePage from './pages/HomePage/HomePage'
import AuthPage from './pages/AuthPage/AuthPage'

import './style/variables.scss'
import './style/global.scss'
import App from './components/App/App'

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement)

root.render(
	<React.StrictMode>
		<ChakraProvider>
			<GlobalStyle />
			<App />
		</ChakraProvider>
	</React.StrictMode>,
)
