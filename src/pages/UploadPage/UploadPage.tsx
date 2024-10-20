import { FC } from 'react'
import MainLayout from '../../layouts/MainLayout/MainLayout'
import DefaultButton from '../../ui/Buttons/DefaultButton/DefaultButton'

import styles from './UploadPage.module.scss'
import { ArrowDownToLine } from 'lucide-react'
import Dropdown from '../../ui/Dropdown/Dropdown'

interface UploadPagePropsI {}

const UploadPage: FC<UploadPagePropsI> = () => {
	return (
		<MainLayout>
			<div className={styles['files']}>
				<div className={styles['files__header']}>
					<Dropdown
						items={['Участник', 'Участник2', 'Участник3']}
						classList={styles['files__dropdown']}
						label='Участники'
					/>
					<form action='post' className={styles['files__uploader']}>
						<div className={styles['files__file']}>
							<label className={styles['files__file-wrapper']}>
								<input type='file' className={styles['files__file-input']} />
								<ArrowDownToLine stroke='#fff' size='18' />
								Выберите файлы
							</label>
						</div>
						<DefaultButton type='submit' text='Отправить'></DefaultButton>
					</form>
				</div>
				{/* <ul className='files__items'>
                    <li className="files__item"></li>
                </ul> */}
			</div>
		</MainLayout>
	)
}

export default UploadPage
