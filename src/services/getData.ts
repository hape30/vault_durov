export const getData = async (url: string) => {
	try {
		const response = await fetch(process.env.REACT_APP_API_URL + url)

		if (!response.ok) {
			throw new Error(`Ошибка получения данных: ${response.status}`)
		}

		const data = await response.json()

		return data
	} catch (error) {
		console.error('Ошибка при получении данных:', error)
		throw error
	}
}
