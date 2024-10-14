import { FC, useState } from 'react'
import {
	ListItem,
	Tab,
	TabIndicator,
	TabList,
	TabPanel,
	TabPanels,
	Tabs,
	UnorderedList,
} from '@chakra-ui/react'
import FluidLayout from '../../layouts/FluidLayout/FluidLayout'
import ReactQuill from 'react-quill'

import styles from './RepoPage.module.scss'
import { Resizable } from 're-resizable'
import { ChevronUp, File, Folder } from 'lucide-react'
import 'react-quill/dist/quill.snow.css'

interface RepoPagePropsI {}

const RepoPage: FC<RepoPagePropsI> = () => {
	const [value, setValue] = useState('fsfes')

	const toolbarOptions = [
		['bold', 'italic', 'underline', 'strike'], // toggled buttons
		['blockquote', 'code-block'],
		['link', 'image', 'video', 'formula'],

		[{ header: 1 }, { header: 2 }], // custom button values
		[{ list: 'ordered' }, { list: 'bullet' }, { list: 'check' }],
		[{ script: 'sub' }, { script: 'super' }], // superscript/subscript
		[{ indent: '-1' }, { indent: '+1' }], // outdent/indent
		[{ direction: 'rtl' }], // text direction

		[{ size: ['small', false, 'large', 'huge'] }], // custom dropdown
		[{ header: [1, 2, 3, 4, 5, 6, false] }],

		[{ color: [] }, { background: [] }], // dropdown with defaults from theme
		[{ font: [] }],
		[{ align: [] }],

		['clean'], // remove formatting button
	]

	return (
		<FluidLayout title='Название репозитория'>
			<Tabs variant='enclosed'>
				<div className='subheader'>
					<TabList>
						<Tab borderTop='none' borderRadius='0px'>
							Файлы
						</Tab>
						<Tab borderTop='none' borderRadius='0px'>
							Слияния
						</Tab>
						<Tab borderTop='none' borderRadius='0px'>
							Настройки
						</Tab>
					</TabList>
				</div>
				<TabIndicator bg='#000' />
				<TabPanels>
					<TabPanel className={styles.content} padding='0'>
						<Resizable
							defaultSize={{
								width: 320,
							}}
							className={styles['files']}>
							<UnorderedList ml='0' pt='10px' className={styles['files-list']}>
								<ListItem className={styles['files-list-item']}>
									<File size='18px' />
									filename.txt
								</ListItem>
								<ListItem className={styles['files-list-item']}>
									<File size='18px' />
									filename.txt
								</ListItem>
								<ListItem className={styles['files-list-item']}>
									<File size='18px' />
									filename.txt
								</ListItem>
								<ListItem>
									<b className={styles['files-list-item']}>
										<Folder size='18px' />
										pathname
										<ChevronUp size='18px' />
									</b>
									<UnorderedList ml='10px' className={styles['files-list']}>
										<ListItem className={styles['files-list-item']}>
											<File size='18px' />
											filename.txt
										</ListItem>
										<ListItem className={styles['files-list-item']}>
											<File size='18px' />
											filename.txt
										</ListItem>
										<ListItem className={styles['files-list-item']}>
											<File size='18px' />
											filename.txt
										</ListItem>
									</UnorderedList>
								</ListItem>
							</UnorderedList>
							<div className={styles['width-control']}></div>
						</Resizable>
						<ReactQuill
							modules={{ toolbar: toolbarOptions }}
							className={styles.editor}
							theme='snow'
							value={value}
							onChange={setValue}
						/>
					</TabPanel>
					<TabPanel>
						<p>two!</p>
					</TabPanel>
				</TabPanels>
			</Tabs>
		</FluidLayout>
	)
}

export default RepoPage
