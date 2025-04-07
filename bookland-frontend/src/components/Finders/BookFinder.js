import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from "react-redux";
import { Button, Space, Select, Skeleton, Pagination, Slider, InputNumber } from 'antd';
import { GetAllparams, GetAuthor, GetAuthors, GetBooks, GetBooksSize, GetGenre, GetGenres, GetOneSeries, GetPublishers, GetSeries, GetTypes } from '../../api/FindBooksApiMethods';


export const BookFinder = ({genreId, authorId, seriesId, inName}) => {
    const [isLoading, setIsLoading] = useState(false)

    const [main, setMain] = useState([]);
    const [size, setSize] = useState(0);

    const [types, setTypes] = useState([]);
    const [series, setSeries] = useState([]);
    const [publishers, setPublishers] = useState([]);
    const [genres, setGenres] = useState([]);
    const [authors, setAuthors] = useState([]);

    const [genre, setGenre] = useState(null);
    const [oneSeries, setOneSeries] = useState(null);
    const [author, setAuthor] = useState(null);
    
        const [params, setParams] = useState({
            type: 0,
            genre: genreId ? genreId : 0,
            publisher: 0,
            series: seriesId ? seriesId : 0,
            inName: inName ? inName : null,
            author: authorId ? authorId : 0,
            prise: 0,
            page: 0,
        })

    const formatData = (data) => {
            const formattedData = data.map(item => ({
                value: item.id,
                label: item.name,
              }));
            return formattedData
        }
    
        const formatAuthorData = (data) => {
            const formattedData = data.map(item => ({
                value: item.id,
                label: item.name + " " + item.secondName + " " + item.lastName,
              }));
            return formattedData
        }
    
        const getInfo = async () => {
                const res = await GetAllparams();
                setTypes(formatData(res.types))
                setGenres(formatData(res.genres))
                setSeries(formatData(res.series))
                setPublishers(formatData(res.publishers))
                setAuthors(formatAuthorData(res.authors))

                setSize(await GetBooksSize(params))
                setMain(await GetBooks(params))
                
                if (genreId){
                    setGenre(await GetGenre(genreId))
                }

                if(seriesId){
                    setOneSeries(await GetOneSeries(seriesId))
                }

                if(authorId){
                    setAuthor(await GetAuthor(authorId))
                }

                if (res)
                    setIsLoading(true)
            }
    
        useEffect(() => {
                getInfo()
        }, []);
    
        const searchGenre = async (value) => {
            if (value){
                setGenres(formatData(await GetGenres(value)))
            } else {
                setGenres(formatData(await GetGenres()))
            }
        }
    
        const searchType = async (value) => {
            if (value){
                setTypes(formatData(await GetTypes(value)))
            } else {
                setTypes(formatData(await GetTypes()))
            }
        }
    
        const searchSeries = async (value) => {
            if (value){
                setSeries(formatData(await GetSeries(value)))
            } else {
                setTypes(formatData(await GetSeries()))
            }
        }
    
        const searchPublishers = async (value) => {
            if (value){
                setPublishers(formatData(await GetPublishers(value)))
            } else {
                setPublishers(formatData(await GetPublishers()))
            }
        }
    
        const searchAuthors = async (value) => {
            if (value){
                setAuthors(formatAuthorData(await GetAuthors(value)))
            } else {
                setAuthors(formatAuthorData(await GetAuthors()))
            }
        }
    
        const handlePageChange = (page) => {
            setParams((prevParams) => ({ ...prevParams, page: page - 1 }));
        };
    
        const clickFind = async () => {
            setSize(await GetBooksSize(params))
            setMain(await GetBooks(params))
        }

        const [lowPrise, setLowPrise] = useState(0);
        const [highPrise, setHighPrise] = useState(100);

        const ChangeLowPrise = (value) => {
            if (value >= highPrise)
                setLowPrise(highPrise)
            else
                setLowPrise(value)
        }

        const ChangeHighPrise = (value) => {
            if (value < lowPrise)
                setHighPrise(lowPrise)
            else
                setHighPrise(value)
        }

        const handleSliderChange = (value) => {
            if (value[0] >= value[1]) {
                setLowPrise(value[1]);
                setHighPrise(value[0]);
              } else {
                setLowPrise(value[0]);
                setHighPrise(value[1]);
              }
          };

    console.log(main)

    return (
        <>
            <h1 style={{width: "100%", textAlign: "center"}}>
                {genre != null && 
                    genre.name
                }
                {oneSeries != null && 
                    oneSeries.name
                }
                {author != null &&
                    author.name + " " + author.lastName
                }
                {genre == null && oneSeries == null && author == null &&
                    "Все книги"
                }
            </h1>
            <div style={{display: "flex", alignItems: "flex-start"}}>
                <div className={"mainBlockStyle" + ' ' + "finderStyle"} style={{marginRight: "15px", textAlign: "center"}}>
                    {isLoading ?
                        <>
                            {!genreId && 
                                <>
                                    <p>Жанр</p>
                                    <Select onChange={value => params.genre = value} showSearch filterOption={false} onSearch={searchGenre}
                                    options={[{value: 0, label: "Любой"}, ...genres]} dropdownStyle={{ width: 175 }} style={{width: "100%", maxWidth: "150px", minWidth: "75px"}}/>
                                </>
                            }
                            
                            {!authorId &&
                                <>
                                    <p>Автор</p>
                                    <Select onChange={value => params.author = value} showSearch filterOption={false} onSearch={searchAuthors}
                                    options={[{value: 0, label: "Любой"}, ...authors]} dropdownStyle={{ width: 200 }} style={{width: "100%", maxWidth: "150px", minWidth: "75px"}}/>
                                </>
                            }

                            <p>Цена</p>
                            <div style={{width: "100%", maxWidth: "150px", minWidth: "50px", margin: "auto"}}>
                                <Slider range value={[lowPrise, highPrise]} onChange={handleSliderChange}/>
                                <Space direction="horizontal">
                                    <InputNumber min={0} max={100} value={lowPrise} style={{width: "100%", minWidth: "25px"}} onChange={ChangeLowPrise}/>
                                    <InputNumber min={0} max={100} value={highPrise} style={{width: "100%", minWidth: "25px"}} onChange={ChangeHighPrise}/>
                                </Space>
                            </div>
                            
                            
                            <p>Тип книги</p>
                            <Select onChange={value => params.type = value} showSearch filterOption={false} onSearch={searchType}
                            options={[{value: 0, label: "Любой"}, ...types]} dropdownStyle={{ width: 200 }} style={{width: "100%", maxWidth: "150px", minWidth: "75px"}}/>

                            {!seriesId && 
                                <>
                                    <p>Серия</p>
                                    <Select onChange={value => params.series = value} showSearch filterOption={false} onSearch={searchSeries}
                                    options={[{value: 0, label: "Любой"}, ...series]} dropdownStyle={{ width: 200 }} style={{width: "100%", maxWidth: "150px", minWidth: "75px"}}/>
                                </>
                            }
                            
                            <p>Издатель</p>
                            <Select onChange={value => params.publisher = value} showSearch filterOption={false} onSearch={searchPublishers}
                            options={[{value: 0, label: "Любой"}, ...publishers]} style={{width: "100%", maxWidth: "150px", minWidth: "75px", marginBottom: "10px"}}/>
                            <Button type="primary" style={{width: "100%", maxWidth: "150px", minWidth: "75px", marginBottom: "20px"}} onClick={clickFind}>Найти</Button>
                        </>
                    :
                        <Skeleton active />
                    }
                </div>
                <div className={"mainBlockStyle"} style={{paddingTop: "15px", paddingBottom: "15px"}}>
                {isLoading ?
                    <div style={{width: "100%"}}>
                        {main.length > 0 ?
                            <div className="shopMainContainer">
                                {main.map((book) => {
                                    if (book.images.length > 0){
                                        const image = book.images[0].location;
                                        return <div key={book.isbn} 
                                            className={"shopCardStyle"}>
                                            <img src={"http://localhost:8080/shop/open/material/" + image} style={{height: "290px"}}/>
                                            <a href='' className='name-main-view-style'>{book.name}</a>
                                            <p className='author-main-view-style'>{book.authors.map((author, index) => (
                                                <React.Fragment key={index}>
                                                <a href={''} className='author-main-view-style'>
                                                    {author.name} {author.lastName}
                                                </a>
                                                {index < book.authors.length - 1 && ', '}
                                                </React.Fragment>
                                            ))}</p>
                                            <p className='prise-main-view-style'>{book.prise} ₽</p>
                                            <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                                                <Button type="primary" style={{width: "150px"}} onClick={console.log(book.isbn)}>Купить</Button>
                                            </div> 
                                        </div>  
                                    } else {
                                        return <div key={book.isbn} 
                                            className={"shopCardStyle"}>
                                            <p className='name-main-view-style'>{book.name}</p>
                                            <p className='author-main-view-style'>{book.authors.map(author => `${author.name} ${author.lastName}`).join(', ')}</p>
                                            <p className='prise-main-view-style'>{book.prise} ₽</p>
                                            <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                                                <Button type="primary" style={{width: "150px"}} onClick={console.log(book.isbn)}>Купить</Button>
                                            </div> 
                                        </div>  
                                    }                                          
                                })}
                            </div>
                            :
                            <div>
                                <h1>По запросу не найдено ни одной книги</h1>
                            </div>
                        }
                        <Pagination
                            align="center"
                            defaultCurrent={1}
                            defaultPageSize={16}
                            showSizeChanger={false}
                            total={size}
                            onChange={handlePageChange}/> 
                    </div>
                :
                <Skeleton active />}
                </div>
            </div>            
        </>
    )
}