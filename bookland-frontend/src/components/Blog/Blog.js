import React, { useEffect, useState } from 'react'
import { GetArticles } from '../../api/BlogApiMethods';
import { Button, Input, Space, Select, Card } from 'antd';


export const Blog = () => {
    const [data, setData] = useState([]);
    const [sortType, setSortType] = useState(true);
    const [typeArticles, setTypeArticles] = useState('ALL');

    const allArticles = async () => {
        const res = await GetArticles(sortType, typeArticles);
        setData(res)
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
    }, [sortType, typeArticles]);

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
                <Button type="primary" style={{ float: "right" }}>добавить статью</Button>   
                </div>
            <hr style={{borderColor: "#F5F5F5"}}/>
            <div className='ArticlesGrid'>
            {data.map((article) => {
                    return <Card
                        key={article.id}
                        className={"mainCardsStyle"}
                        bordered={false}
                        hoverable
                    >
                        <h1>{article.title} </h1>
                        <p>{article.description}</p>
                        <p>{article.publication}</p>
                    </Card>
                })}
            </div>  
            </Space>
        </div>
    )
}
