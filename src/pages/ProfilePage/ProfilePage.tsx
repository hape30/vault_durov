import { FC } from 'react'

import FluidLayout from '../../layouts/FluidLayout/FluidLayout'

import styles from './ProfilePage.module.scss'
import { Input } from '@chakra-ui/react'
import DefaultButton from '../../ui/Buttons/DefaultButton/DefaultButton'

interface ProfilePagePropsI {}

const ProfilePage: FC<ProfilePagePropsI> = () => {
	return (
		<FluidLayout>
			<div className={styles['profile']}>
				<aside className={styles['profile__sidebar']}>
					<nav className={styles['profile__navigation']}>
						<ul className={styles['profile__list']}>
							<li className={styles['profile__list-item']}>
								<a href='#' className='profile__list-link'>
									Профиль
								</a>
							</li>
							<li className={styles['profile__list-item']}>
								<a href='#' className={styles['profile__list-link']}>
									Настройки
								</a>
							</li>
							<li className={styles['profile__list-item']}>
								<a href='#' className={styles['profile__list-link']}>
									Выйти
								</a>
							</li>
						</ul>
					</nav>
				</aside>
				<div className={styles['profile__content']}>
					<div className={styles['user']}>
						<div className={styles['user__row'] + styles['user__photo']}>
							<img
								src='https://masterpiecer-images.s3.yandex.net/f08c9970279011eebc764ad64b19119b:upscaled'
								alt=''
								className={styles['user__photo-img']}
							/>
						</div>
						<div className={styles['user__row']}>
							<Input className={styles['user__input']} placeholder='Username' />
							<Input className={styles['user__input']} placeholder='Email' />
							<Input className={styles['user__input']} placeholder='Phone' />
						</div>
						<div className={styles['user__row']}>
							<DefaultButton text='Сохранить' />
						</div>
					</div>
				</div>
			</div>
		</FluidLayout>
	)
}

export default ProfilePage
