import AuthPage from '../../pages/AuthPage/AuthPage'
import ProfilePage from '../../pages/ProfilePage/ProfilePage'

export const AppRouterConfig = [
	{
		path: '/auth',
		element: <AuthPage />,
	},
	{
		path: '/profile',
		element: <ProfilePage />,
	},
]
