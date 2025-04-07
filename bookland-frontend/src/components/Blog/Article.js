import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from "react-redux";
import { useParams, useNavigate } from 'react-router-dom';
import { CreateComment, DeleteArticle, DeleteComment, GetArticle, GetComments, UpdateComment } from '../../api/BlogApiMethods';
import { Button, Modal, Space, Skeleton, Avatar, Input} from 'antd';
import { UserOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { getIconUrl } from '../Urls';
import { CheckToken, RefreshToken } from '../../api/UserApiMethods';
import { deleteToken } from "../../store/reducers/TokenSlice"
import { deleteUser } from "../../store/reducers/UserSlice"

export const Article = () => {
    const { TextArea } = Input;
    const { article_id } = useParams();
    const [article, setArticle] = useState(null);
    const [comments, setComments] = useState([]);
    const [commentText, setCommentText] = useState('');
    const [commentStatus, setCommentStatus] = useState('');

    const [commentEdit, setCommentEdit] = useState(null);
    const [commentEditText, setCommentEditText] = useState('');
    const [commentEditStatus, setCommentEditStatus] = useState('');
    const { id } = useSelector(state => state.user);
    const { token, refresh } = useSelector(state => state.Token);
    const navigation = useNavigate()
    const dispatch = useDispatch();

    const [isModalOpen, setIsModalOpen] = useState(false);
    const showModal = (comment) => {
        setCommentEdit(comment)
        setCommentEditText(comment.text)
        setIsModalOpen(true);
    };
    const handleOk = async () => {
        setIsModalOpen(false);

        const updateComment = {
            ...commentEdit,
            text: commentEditText
        }

        const res = await UpdateComment(updateComment, token)
            if (res === 403){
                const config = {
                    refreshToken: refresh
                }
                const checkToken = await RefreshToken(dispatch, config)
                if (checkToken){
                    await UpdateComment(updateComment, token)
                    navigation(`/blog/` + article_id)
                } else {
                    dispatch(deleteToken())
                    dispatch(deleteUser())
                    navigation(`/login`)
                }                                
            }
            getComments()
    };
    const handleCancel = () => {
        setIsModalOpen(false);
    };

    const getArticle = async () => {
        const res = await GetArticle(article_id);
        if (res != null){
            setArticle(res)
        }
    }

    const getComments = async () => {
        const res = await GetComments(article_id);
        if (res != null){
            setComments(res)
        }
    }
    
    useEffect(() => {
        getArticle()
        getComments()
    }, [article_id]);

    const sendComment = async () => {
        if (commentText !== ''){
            const config = {
                text: commentText,
                articleId: article.id,
                userId: id
            }
    
            const res = await CreateComment(config, token)
            if (res === 403){
                const config = {
                    refreshToken: refresh
                }
                const checkToken = await RefreshToken(dispatch, config)
                if (checkToken){
                    await CreateComment(config, token)
                    navigation(`/blog/` + article_id)
                } else {
                    dispatch(deleteToken())
                    dispatch(deleteUser())
                    navigation(`/login`)
                }                                
            }
            getComments()
        } else {
            setCommentStatus("error")
        }
    }

    const deleteComment = async(commentId) =>{
        const res = await DeleteComment(commentId, token)
        if (res === 403){
            const config = {
                refreshToken: refresh
            }
            const checkToken = await RefreshToken(dispatch, config)
            if (checkToken){
                await DeleteComment(commentId, token)
                navigation(`/blog/` + article_id)
            } else {
                dispatch(deleteToken())
                dispatch(deleteUser())
                navigation(`/login`)
            }                                
        }
        getComments()
    }

    const cardChange = async (id) => {
        const checkToken = await CheckToken(token)
        if (checkToken){
            navigation(`/editPost/${id}`)
        } else {
            const config = {
                refreshToken: refresh
            }
            const checkToken = await RefreshToken(dispatch, config)
            if (checkToken){
                navigation(`/editPost/${id}`)
            } else {
                dispatch(deleteToken())
                dispatch(deleteUser())
                navigation(`/login`)
            }
        }
    }

    const deleteArticle = async () => {
        const res = await DeleteArticle(article_id, token)
        if (res === 403){
            const config = {
                refreshToken: refresh
            }
            const checkToken = await RefreshToken(dispatch, config)
            if (checkToken){
                await DeleteArticle(article_id, token)
                navigation(`/blog`)
            } else {
                dispatch(deleteToken())
                dispatch(deleteUser())
                navigation(`/login`)
            }                                
        }
        navigation(`/blog`)
    }

    return (
        <div style={{position: "relative"}} className='mainBodyStyle'>
            <Space direction="vertical" className={"mainBlockStyle" + ' ' + "mainBlogStyle"}>
                {article ? (
                    <>
                        {id && id === article.author.author_id && 
                            <Space direction="horizontal" style={{float: "right", marginRight: "10px"}}>
                                <Button color="purple" variant="outlined" style={{float: "right", marginTop: "15px"}} onClick={() => cardChange(article_id)}>Изменить</Button>
                                <Button color="danger" variant="solid" style={{float: "right", marginTop: "15px"}} onClick={() => deleteArticle()}>Удалить</Button>
                            </Space>
                        }
                        
                        <div>
                            <h1 style={{textAlign: "center", color: "#7146bd", fontSize: "52px", marginTop: "1px", marginBottom: "10px"}}>{article.title}</h1>
                            <div className="article-content" dangerouslySetInnerHTML={{ __html: article.text }} style={{fontSize: "20px"}} />
                            <div style={{display: "flex"}}>
                                <p style={{color: "grey", fontSize: "20px"}}>{article.publication}</p>
                                <Space direction="horizontal" style={{position: "absolute", right: "0", marginRight: "10px"}}>
                                    <p style={{marginRight: "5px", fontSize: "20px"}}>{article.author.author_name}</p>
                                    {article.author.author_image? 
                                        <Avatar size={45} src={getIconUrl + article.author.author_image} />
                                    : 
                                        <Avatar size={45} icon={<UserOutlined />} />
                                    }                                
                                </Space>
                            </div>
                            
                        </div>
                        <hr/>
                        <div style={{display: "flex", justifyContent: "center"}}>
                            {id ? (
                                <div>
                                    <Space direction="vertical">
                                        <Space direction="horizontal">
                                            <p>Коментарий</p>
                                            <TextArea showCount status={commentStatus} rows={1} maxLength={255} style={{ width: '250px' }} onChange={(e) => {setCommentStatus(''); setCommentText(e.target.value)}}/>
                                        </Space>
                                        <Button type="primary" style={{float: "right", marginTop: "15px"}} onClick={() => sendComment()}>Отправить</Button>
                                    </Space>
                                    
                                </div>
                            ) : (
                                <div style={{ textAlign: "center" }}>
                                    <p>Сообщения могут оставлять только зарегистрированные пользователи</p>
                                    <Space direction="horizontal">
                                        <Button type="primary" onClick={() => navigation(`/login`)}>Войти</Button>
                                        <Button type="primary" onClick={() => navigation(`/registration`)}>Создать аккаунт</Button>
                                    </Space>
                                </div>    
                            )}
                        </div>
                        <hr/>
                        <div>
                            {comments.length > 0 ?
                            <div>
                                {comments.map((comment) => {
                                    return <div style={{display: "flex"}} key={comment.id}>
                                                {comment.author.icon? 
                                                    <Avatar size={35} src={getIconUrl + comment.author.icon} />
                                                : 
                                                    <Avatar size={35} icon={<UserOutlined />} />
                                                }
                                                <p style={{marginLeft: "10px", marginRight: "5px", marginTop: "1px", fontSize: "20px"}}>{comment.author.name} - </p> 
                                            <p style={{marginRight: "5px", marginTop: "1px", fontSize: "20px"}}>{comment.text}</p>
                                            {id && id === comment.author.id && (
                                                <>
                                                    <Button color="purple" variant="outlined" shape="circle" 
                                                    icon={<EditOutlined />} size='small' 
                                                    style={{marginTop: "4px", marginLeft: "5px", marginRight: "5px"}} 
                                                    onClick={() => showModal(comment)}/>
                                                    <Button color="danger" variant="outlined" shape="circle"
                                                     icon={<DeleteOutlined />} size='small' style={{marginTop: "4px"}} 
                                                     onClick={() => deleteComment(comment.id)}/>
                                                    <Modal open={isModalOpen} onOk={handleOk} onCancel={handleCancel} width={450}>
                                                        <TextArea showCount status={commentEditStatus} rows={1} maxLength={255} 
                                                        style={{marginTop: "30px", marginBottom: "15px", width: '400px' }} 
                                                        onChange={(e) => {setCommentEditStatus(''); setCommentEditText(e.target.value)}} 
                                                        value={commentEditText}/>
                                                    </Modal>
                                                </>
                                            )}
                                                                  
                                        </div>
                                })}
                            </div>
                            :
                            <div style={{ textAlign: "center" }}>
                                <p >Комментарии отсутствуют</p>
                            </div> 
                            }
                        </div>
                    </>
                ) : (
                    <Skeleton active />
                )}
            </Space>
        </div>
    )
}