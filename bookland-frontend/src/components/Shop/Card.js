import React, { useEffect, useState }  from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from "react-redux";
import { Button, Space, Skeleton, Modal, Pagination, Carousel, Rate, Input, Avatar } from 'antd';
import { CheckCircleOutlined, CloseCircleOutlined, UserOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons'
import { GetOneBook } from '../../api/FindBooksApiMethods';
import { getBookImage, getIconUrl } from '../Urls';
import '../../index.css'
import './Styles.css'
import { ChangeReview, CreateReview, DeleteReview, GetReviews, GetReviewsSize } from '../../api/ReviewApiMethods';
import { RefreshToken } from '../../api/AuthApiMethods';
import { deleteToken } from '../../store/reducers/TokenSlice';
import { deleteUser } from '../../store/reducers/UserSlice';
import { Buying } from '../buy/Buying';

export const Card = () => {
    const navigation = useNavigate();

    const { id } = useSelector(state => state.user);
    const { token, refresh } = useSelector(state => state.Token);
    const dispatch = useDispatch();

    const { card_id } = useParams();
    const [card, setCard] = useState(null)

    const { TextArea } = Input;
    const [commentStatus, setCommentStatus] = useState('');
    const [review, setReview] = useState({
        author_id: null,
        text: '',
        book_id: Number(card_id),
        grade: 0
    });

    const [reviewsSize, setReviewsSize] = useState(0)
    const [page, setPage] = useState(0)
    const [reviews, setReviews] = useState([])

    const getInfo = async () => {
        const res = await GetOneBook(card_id)
        setCard(res)

        const size = await GetReviewsSize(card_id)
        setReviewsSize(size)

        const r = await GetReviews(page, card_id)
        setReviews(r)
    }
    
    useEffect(() => {
        getInfo()
    }, [card_id]);

    const [isModalOpen, setIsModalOpen] = useState(false);
  const showModal = (r) => {
    if (r != null){
        setReview(r)
    }
    setIsModalOpen(true);
  };
  const handleOk = async () => {
    if (review.text.length != 0){
        let res
        if (!review.id){
            review.author_id = id
            res = await CreateReview(review, token)
        } else {
            res = await ChangeReview(review, token)
        }
        
        if (res === 403){
            const config = {
                refreshToken: refresh
            }
            const checkToken = await RefreshToken(dispatch, config)
            if (checkToken){
                if (!review.id){
                    await CreateReview(review, token)
                } else {
                    await ChangeReview(review, token)
                }
            } else {
                dispatch(deleteToken())
                dispatch(deleteUser())
                navigation(`/login`)
            }                                
        }

        const size = await GetReviewsSize(card_id)
        setReviewsSize(size)
        const r = await GetReviews(page, card_id)
        setReviews(r)

        setIsModalOpen(false);
        setReview({
            id: null,
            author_id: null,
            text: '',
            book_id: Number(card_id),
            grade: 5
        })
    } else {
        setCommentStatus("error")
    }
  };
  const handleCancel = () => {
    setIsModalOpen(false);
    setReview({
        id: null,
        author_id: null,
        text: '',
        book_id: Number(card_id),
        grade: 5
    })
  };
  
  const deleteReview = async (id) => {
        const res = DeleteReview(id, token)
        if (res === 403){
            const config = {
                refreshToken: refresh
            }
            const checkToken = await RefreshToken(dispatch, config)
            if (checkToken){
                DeleteReview(id, token)
            } else {
                dispatch(deleteToken())
                dispatch(deleteUser())
                navigation(`/login`)
            }                                
        }

        const size = await GetReviewsSize(card_id)
        setReviewsSize(size)
        const r = await GetReviews(page, card_id)
        setReviews(r)
  }

    const handlePageChange = async (page) => {
        const r = await GetReviews(page - 1, card_id)
        setReviews(r)
    };

    return (
        <div className='mainBodyStyle' >
            {card ? 
                <>
                <h1 style={{textAlign: "center", paddingTop: "15px"}}>{card.name}</h1>
                <div className={"mainBlockStyle"} style={{paddingBottom: "15px", paddingTop: "15px", width: "865px", marginBottom: '20px', marginLeft: 'auto', marginRight: 'auto'}}>
                    <div className='cardInfoStyle'>
                        <div>
                        <Carousel arrows infinite>
                            {card.images.map((image) => {
                                return <div key={image.id}>
                                    <img  src={getBookImage + image.location} className='cardImageStyle'/>
                                </div> 
                            })}
                        </Carousel>
                        </div>
                        <div className='infoContainerMain'>
                            <div className='cardInfoContainer'>
                                <p className='cardInfoLabel'>Серия</p>
                                <p className='cardInfoText'>{card.series.name}</p>
                                <p className='cardInfoLabel'>Издательство</p>
                                <p className='cardInfoText'>{card.series.publisher.name}</p>
                                <p className='cardInfoLabel'>Тип обложки</p>
                                <p className='cardInfoText'>{card.type.name}</p>
                                <p className='cardInfoLabel'>Автор</p>
                                <p className='cardInfoText'>{card.authors.map(author => {
                                    return (author.name + ' ' + (author.secondName ? author.secondName : '')  + ' ' + author.lastName)
                                })}</p>
                                <p className='cardInfoLabel'>Жанр</p>
                                <p className='cardInfoText'>{card.genre.name}</p>
                                <p className='cardInfoLabel'>Страниц</p>
                                <p className='cardInfoText'>{card.pages}</p>
                                <p className='cardInfoLabel'>Год изадния</p>
                                <p className='cardInfoText'>{card.release}</p>
                            </div>
                            <div style={{display: 'flex', flexDirection: 'column', marginTop: "15px", width: "90%", height: "100%",  border: "1px solid #ccc", borderRadius: "10px"}}>
                                <h2 style={{marginTop: '5px', textAlign: 'center'}}>Заказ и получение</h2>
                                <div style={{display: 'grid', gridTemplateColumns: "repeat(2, 1fr)", height: "100%"}}>
                                    <div style={{borderTop: "1px solid #ccc", borderRight: "1px solid #ccc", padding: '20px'}}>
                                        <h1 className='cardInfoText' style={{fontSize: "48"}}>{card.prise} ₽</h1>
                                        {card.quantity > 0 ?
                                                <div>
                                                    <div style={{display: "flex"}}>
                                                        <CheckCircleOutlined style={{color: "green", fontSize: '30px', marginRight: '15px'}}/>
                                                        <p>В наличии</p>
                                                    </div>
                                                    <Buying isbn={card.isbn} quantity={card.quantity}/>
                                                </div>
                                            :
                                                <div style={{display: "flex"}}>
                                                    <CloseCircleOutlined style={{color: "red", fontSize: '25px', marginRight: '15px'}}/>
                                                    <p>Нет наличии</p>
                                                </div>
                                        }
                                    </div>
                                    <div style={{borderTop: "1px solid #ccc", padding: '20px'}}>
                                        <p style={{marginBottom: '1px', marginTop: '1px'}}>В магазине</p>
                                        <p style={{fontSize: '14px', color: "gray", marginTop: '1px', marginBottom: '0px'}}>бесплатно</p>
                                        <p style={{marginBottom: '1px', marginTop: '1px'}}>курьером до дома </p>
                                        <p style={{fontSize: '14px', color: "gray", marginTop: '1px', marginBottom: '0px'}}>от 150 ₽</p>
                                        <p style={{marginBottom: '1px', marginTop: '1px'}}>Почтой России</p>
                                        <p style={{fontSize: '14px', color: "gray", marginTop: '1px', marginBottom: '1px'}}>от 100 ₽</p>
                                    </div>
                                </div>
                                
                                
                        </div>
                        </div>
                        
                    </div>
                    <div>
                        <h2 style={{ textAlign: 'center'}}>Описание</h2>
                        {card.about.split('\n').map((text, index) => (
                            <p key={index} style={{ textIndent: "30px" }}>
                                {text}
                            </p>
                        ))}
                    </div>
                </div>
                <div className={"mainBlockStyle"} style={{width: "865px", height: "100%", paddingBottom: '10px', marginLeft: 'auto', marginRight: 'auto'}}>
                    {id ? (
                        <div style={{marginLeft: 'auto', marginRight: 'auto', paddingTop: "10px", textAlign: 'center'}}>
                            <p>Уже читали эту книгу? Поделитесь вашим мнением!</p>
                            <Button type="primary" onClick={() => showModal()}>Написать отзыв</Button>
                            <Modal title="Отзыв" open={isModalOpen} onOk={handleOk} onCancel={handleCancel} width={400}>
                                <Space direction="vertical">
                                    <Space direction="horizontal">
                                        <p>Отзыв</p>
                                        <TextArea showCount status={commentStatus} value={review.text} maxLength={1024} style={{ width: '300px' }} onChange={(e) => {setCommentStatus(''); setReview((r) => ({...r, text: e.target.value}))}}/>
                                    </Space>
                                    <Space direction="horizontal">
                                        <p style={{marginRight: "10px"}}>Оценка</p>
                                        <Rate count={10} value={review.grade} character={({ index = 0 }) => index + 1} onChange={(e) => {setReview((r) => ({...r, grade: e}))}} style={{color: "#7146bd"}}/>
                                    </Space>
                                </Space>                       
                            </Modal>
                        </div>
                    ) : (
                        <div style={{ textAlign: "center", paddingTop: "10px", paddingBottom: "5px" }}>
                            <p>Сообщения могут оставлять только зарегистрированные пользователи</p>
                            <Space direction="horizontal">
                                <Button type="primary" onClick={() => navigation(`/login`)}>Войти</Button>
                                <Button type="primary" onClick={() => navigation(`/registration`)}>Создать аккаунт</Button>
                            </Space>
                        </div>    
                    )}
                    <div>
                        {reviews.length > 0 &&
                        <>
                            <hr/>
                            {reviews.map((r) => (
                                <div style={{marginLeft: "15px"}} key={r.id}>
                                    <Space direction="horizontal">
                                        {r.user.icon? 
                                            <Avatar size={35} src={getIconUrl + r.user.icon} />
                                        : 
                                            <Avatar size={35} icon={<UserOutlined />} />
                                        }
                                        <p>{r.user.name}</p>
                                        <p style={{float: "right", color: "#a39e9e"}}>{r.publication}</p>
                                        {id && id === r.user.id && (
                                                <>
                                                    <Button color="purple" variant="outlined" shape="circle" 
                                                    icon={<EditOutlined />} size='small' 
                                                    style={{marginTop: "4px", marginLeft: "5px"}} 
                                                    onClick={() => showModal(r)}/>
                                                    <Button color="danger" variant="outlined" shape="circle"
                                                     icon={<DeleteOutlined />} size='small' style={{marginTop: "4px"}} 
                                                     onClick={() => {deleteReview(r.id)}}/>
                                                </>
                                            )}
                                    </Space>
                                    <p style={{marginTop: "5px", marginBottom: "5px"}}>Оценка: {r.grade} из 10</p>
                                    <p style={{marginTop: "5px", marginBottom: "5px"}}>{r.text}</p>
                                </div>
                            ))}
                            <Pagination
                                align="center"
                                defaultCurrent={1}
                                defaultPageSize={30}
                                showSizeChanger={false}
                                total={reviewsSize}
                                onChange={handlePageChange}
                                style={{marginTop: "10px"}}/> 
                        </>
                        }
                    </div>
                </div>
                </>
            :
                <Skeleton active />
            }
            
        </div>
    )
}