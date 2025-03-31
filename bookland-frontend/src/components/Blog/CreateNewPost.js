import React, { useEffect, useState } from 'react'
import { Button, Input, Space, Radio, message } from 'antd';
import { useParams, useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from "react-redux";
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css'
import { CreateArticle, GetArticle, UpdateArticle } from '../../api/BlogApiMethods';
import { RefreshToken } from '../../api/UserApiMethods';
import { deleteToken } from "../../store/reducers/TokenSlice"
import { deleteUser } from "../../store/reducers/UserSlice"

export const CreateNewPost = () =>{
    const { article_id } = useParams();
    const navigation = useNavigate()
    const dispatch = useDispatch();
    const { id } = useSelector(state => state.user);
    const { token, refresh } = useSelector(state => state.Token);
    const [titleStatus, setTitleStatus] = useState('');
    const { TextArea } = Input;
    const styleBlack = {
        color: "black"
    }
    const styleRed = {
        color: "red"
    }
    const [text, setText] = useState('');
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [type, setType] = useState("BOOKS");

    const [isLoaded, setIsLoaded] = useState(false);

    const toolbarOptions = {
        toolbar: [
            [{ 'header': [1, 2, 3, false] }],
            ['bold', 'italic', 'underline', 'strike'],        
            ['blockquote'],
            ['link', 'image', 'video'],
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],
            [{ 'indent': '-1'}, { 'indent': '+1' }],         
            [{ 'color': [] }, { 'background': [] }],          
            [{ 'align': [] }],
          ]
    };

    const getArticle = async () => {
        const res = await GetArticle(article_id);
        if (res){
            setType(res.type)
            setTitle(res.title)
            setDescription(res.description)
            setText(res.text)
        }
    }

    React.useEffect(() => {
        if (article_id){
            getArticle();
        }
        setIsLoaded(true)
    }, [article_id])

    const Create = async () => {
        if (title !== "" && text.length >= 10){
            let config;
            let res;
            if (article_id){
                config = {
                    id: article_id,
                    title: title,
                    description: description,
                    type: type,
                    text: text,
                    author_id: id,
                }
                res = await UpdateArticle(config, token)
            } else {
                config = {
                    title: title,
                    description: description,
                    type: type,
                    text: text,
                    author_id: id,
                }
                res = await CreateArticle(config, token)
            }
            
            if (res === 403){
                const config = {
                    refreshToken: refresh
                }
                const checkToken = await RefreshToken(dispatch, config)
                if (checkToken){
                    if (article_id){
                        await UpdateArticle(config, token)
                        navigation(`/blog/${article_id}`)
                    } else {
                        await CreateArticle(config, token)
                        navigation(`/blog`)
                    }
                } else {
                    dispatch(deleteToken())
                    dispatch(deleteUser())
                    navigation(`/login`)
                }
            } else {
                navigation(`/blog`)
            }
        } else {
            if (title === ""){
                setTitleStatus("error")
            }
            if (text.length < 10){
                message.open({
                    type: 'error',
                    content: 'Сообщение должно содержать хотябы 10 символов',
                    duration: 5,
                    style: {
                        marginTop: '50px',
                        border: '1px',
                        borderColor: 'red'
                    }
                })
            }
        }
        
    }

    const Cancle = () => {
        navigation(`/blog`)
    }

    return (
        
        <div className='mainBodyStyle'>
            <Space direction="vertical" className={"mainBlockStyle" + ' ' + "mainBlogStyle"}>
                <Space direction="horizontal" style={{marginBottom: "-25px"}}>
                    <p>Загаловок</p>
                    <p style={title.length === 125? styleRed: styleBlack}>{title.length}/125</p>
                </Space>
                <Input status={titleStatus} maxLength={125} value={title} onChange={(e) => {setTitleStatus('')
                    setTitle(e.target.value)}}/>        
                
                
                <Space direction="horizontal" style={{marginBottom: "-25px"}}>
                    <p>Описание</p>
                    <p style={description.length === 255? styleRed: styleBlack}>{description.length}/255</p>
                </Space>
                <TextArea rows={3} maxLength={255} value={description} onChange={(e) => {setDescription(e.target.value)}}/>

                <p style={{marginBottom: "-5px"}}>О чём будет ваша статья?</p>
                {isLoaded &&  // Условный рендеринг Radio.Group
                    <Radio.Group onChange={(e) => setType(e.target.value)} value={type}>
                        <Radio.Button value="BOOKS">О книге</Radio.Button>
                        <Radio.Button value="SHOP">О магазине</Radio.Button>
                        <Radio.Button value="OTHER">О чём-нибудь другом</Radio.Button>
                    </Radio.Group>
                }

                {type === "BOOKS" &&
                    <p style={{marginBottom: "-5px"}}>О какой книге идёт речь?</p>
                }

                <p style={{marginBottom: "-5px"}}>Текст статьи</p>
                <ReactQuill  value={text} onChange={setText} modules={toolbarOptions}/>

                <Space direction="horizontal" style={{ float: "right" }}>
                    <Button type="primary" onClick={Create}>OK</Button>
                    <Button onClick={Cancle}>Отмена</Button>
                </Space>
                
            </Space>
        </div>
    )
}