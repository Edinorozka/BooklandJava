import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { GetArticles, GetArticlesSize } from '../../api/BlogApiMethods';
import { Button, Space, Select, Card, Skeleton, Pagination, Avatar, Carousel } from 'antd';
import { useDispatch, useSelector } from "react-redux";
import { blogUrlMaterial, getIconUrl } from '../Urls';
import { CheckToken, RefreshToken } from '../../api/UserApiMethods';
import { deleteToken } from "../../store/reducers/TokenSlice"
import { deleteUser } from "../../store/reducers/UserSlice"
import { UserOutlined } from '@ant-design/icons';


export const Blog = () => {
    const [data, setData] = useState([]);
    const [size, setSize] = useState(0);
    const [current, setCurrent] = useState(0);
    const [isLoading, setIsLoading] = useState(false)
    const [sortType, setSortType] = useState(true);
    const [typeArticles, setTypeArticles] = useState('ALL');
    const { name, role } = useSelector(state => state.user);
    const { token, refresh } = useSelector(state => state.Token);
    const dispatch = useDispatch();
    const navigation = useNavigate()

    const allArticles = async () => {
        const res = await GetArticles(sortType, typeArticles, current);
        setData(res)
        if (res != null)
            setIsLoading(true)
    }

    const sizeArticles = async () => {
        const res = await GetArticlesSize(typeArticles);
        setSize(res)
    }

    const handleChangeSort = (value) => {
        setSortType(value)
        console.log(`selected ${value}`);
    };

    const handleChangeType = (value) => {
        setTypeArticles(value)
        console.log(`selected ${value}`);
    };
    
    useEffect(() => {
        allArticles()
        sizeArticles()
    }, [sortType, typeArticles, current]);

    const CreateNewPostClick = async () => {
        const checkToken = await CheckToken(token)
        if (checkToken){
            navigation(`/createPost`)
        } else {
            const config = {
                refreshToken: refresh
            }
            const checkToken = await RefreshToken(dispatch, config)
            if (checkToken){
                navigation(`/createPost`)
            } else {
                dispatch(deleteToken())
                dispatch(deleteUser())
                navigation(`/login`)
            }
        }
        
    }

    const CardOpen = (id) => {
        navigation(`/blog/${id}`)
    }

    return (
        <div className='mainBodyStyle'>
            <Space direction="vertical" className={"mainBlockStyle" + ' ' + "mainBlogStyle"}>
                <div style={{width: "100%", display: "flex"}}>
                    <Space direction="horizontal" style={{marginLeft: "1vh", width: "100%"}}>
                    <Select
                        defaultValue="true"
                        style={{
                            width: 150,
                        }}
                        onChange={handleChangeSort}
                        options={[
                            {
                                value: 'true',
                                label: 'Сначала новые',
                            },
                            {
                                value: 'false',
                                label: 'Сначала старые',
                            },
                        ]}
                    />
                    <Select
                        defaultValue="ALL"
                        style={{
                            width: 150,
                        }}
                        onChange={handleChangeType}
                        options={[
                            {
                                value: 'ALL',
                                label: "Все статьи",
                            },
                            {
                                value: 'BOOKS',
                                label: 'О книгах',
                            },
                            {
                                value: 'SHOP',
                                label: 'О магазине',
                            },
                            {
                                value: 'OTHER',
                                label: 'Другие статьи',
                            },
                        ]}
                    />
                    </Space> 
                {name &&
                    <Button type="primary" style={{ float: "right" }} onClick={() => CreateNewPostClick()}>Добавить статью</Button>   
                }
                  
                </div>
            <hr style={{borderColor: "#F5F5F5"}}/>
            {isLoading ? 
                <div>
                {data.length > 0 ? 
                    <div>
                        {data.map((article) => {
                        return <Card
                                key={article.id}
                                className={"mainCardsStyle"}
                                bordered={false}
                                hoverable
                                onClick={() => CardOpen(article.id)}
                            >
                                <div className="text" style={{display: "flex", marginTop: "-25px"}}>
                                    {article.author.author_image? 
                                        <Avatar size={35} src={getIconUrl + article.author.author_image} style={{marginTop: "18px"}} />
                                    : 
                                        <Avatar size={35} icon={<UserOutlined />} style={{marginTop: "18px"}}/>
                                    }            
                                    <p style={{marginRight: "20px"}}>{article.author.author_name}</p>
                                    <p style={{color: "grey"}}>{article.publication}</p>
                                </div>
                                <h1 style={{ marginTop: '-10px', textAlign: "center", color: "#7146bd"}}>{article.title} </h1>
                                <Carousel
                                    autoplay
                                    arrows
                                    infinite={false}>
                                    {article.materials.map((material) => {
                                        return <img key={material.id} src={blogUrlMaterial + '/' + material.location} alt={material.text} className='cardMaterial'/>
                                    })}
                                </Carousel>
                                <p className="text">{article.description}</p>
                            </Card>
                        })}
                    <Pagination
                        align="center"
                        defaultCurrent={1}
                        defaultPageSize={20}
                        showSizeChanger={false}
                        total={size}
                        onChange={(page) => {setCurrent(page)}}/>
                    </div>
                    :
                    <div className="text nullObjectBlockStyle">
                        <p>К сожалению по данной теме нет ещё ни одной статьи</p>
                        <p>Станьте первым!!!</p>
                        <Button type="primary"
                        style={{display: "block", marginLeft: "auto", marginRight: "auto"}}
                        onClick={() => CreateNewPostClick()}>добавить статью</Button>  
                    </div>
                    }
                </div>
                :
                <Skeleton active />
            }
              
            </Space>
        </div>
    )
}
