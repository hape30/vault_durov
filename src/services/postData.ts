export const postData = async (url: string, data: any) => {
	try {
		const response = await fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(data),
		})

		if (!response.ok) {
			throw new Error(`Ошибка отправки данных: ${response.status}`)
		}

		const responseData = await response.json()

		return responseData
	} catch (error) {
		console.error('Ошибка при отправке данных:', error)
		throw error
	}
}
