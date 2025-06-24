import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from "react-redux";
import { Button, Space, Carousel} from 'antd';
import '../../index.css'
import { BookFinder } from '../Finders/BookFinder';
import { GetAllGenre } from '../../api/FindBooksApiMethods';
import { getBookImage, getShopMaterialsImage } from '../Urls';
import { GetBanners } from '../../api/BannerApiMethods';
import { Buying } from '../buy/Buying';


export const Shop = () =>{
    const navigation = useNavigate()
    const [banners, setBanners] = useState(null)
    const [genres, setGenres] = useState([])

    const getInfo = async () => {
        const b = await GetBanners()
        setBanners(b)
        const g = await GetAllGenre()
        setGenres(g)
    }

    useEffect(() => {
        getInfo()
    }, []);

    return (
        <div className='mainBodyStyle'>
            <div style={{borderRadius: "15px", minWidth: "775px", marginBottom: "25px", boxShadow: "1px 1px 3px 1px rgba(0, 0, 0, 0.3)"}}>
                {banners &&
                    <Carousel arrows infinite autoplay autoplaySpeed={10000} style={{height: "400px"}}>
                        {banners.sort((a, b) => a.id - b.id).map((banner) => {
                            return <div key = {banner.id} 
                            style={{
                                borderRadius: "15px",
                                display: "flex"}}>
                                {banner.image ? 
                                    <img src={getBookImage + banner.images} style={{
                                        width: "auto",
                                        height: "399px",
                                        borderRadius: "15px"}}/>
                                    :
                                    banner.book ? 
                                        <div style={{display: "flex"}}>
                                            <div>
                                                <img src={getBookImage + banner.book.images.location} style={{
                                                    width: "auto",
                                                    height: "399px",
                                                    borderTopLeftRadius: "15px",
                                                    borderBottomLeftRadius: "15px"}}/>
                                            </div>
                                            <div style={{paddingLeft: "15px",
                                                paddingRight: "10px",
                                                height: "400px",
                                                borderTopRightRadius: "15px",
                                                borderBottomRightRadius: "15px",
                                                background: banner.color,
                                                backgroundImage: banner.background ? `url(${getShopMaterialsImage + banner.background})` : "none",
                                                backgroundSize: "cover",
                                                backgroundPosition: "center"
                                                }}>
                                                <h1 style={{color: banner.textColor, fontFamily: banner.textStyle}}>{banner.type}: {banner.title ? banner.title : banner.book.name}</h1>
                                                <p className='banner-main-text' style={{color: banner.textColor, fontSize: banner.textSize, fontFamily: banner.textStyle}}>{banner.text ? banner.text : banner.book.about}</p>
                                                <a className='banner-link' 
                                                    style={{color: banner.textColor, fontFamily: banner.textStyle, fontSize: banner.textSize}} 
                                                    onClick={() => navigation(`card/${banner.book.isbn}`)}>Подробнее</a>
                                                <Space direction="horizontal">
                                                    <p style={{color: banner.textColor, fontFamily: banner.textStyle, fontSize: banner.textSize}}>{banner.book.prise} ₽</p>
                                                    {banner.book.quantity > 0 ?
                                                        <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                                                            <Buying isbn={banner.book.isbn} quantity={banner.book.quantity}/>
                                                        </div> 
                                                    :
                                                        <p style={{textAlign: "center", margin: "0px"}}>Нет в наличии</p>
                                                    }
                                               </Space>
                                            </div>
                                        </div>
                                    :
                                    <div style={{padding: "25px",
                                        height: "350px",
                                        borderRadius: "15px",
                                        background: banner.color,
                                        backgroundImage: banner.background ? `url(${getShopMaterialsImage + banner.background})` : "none",
                                        backgroundSize: "cover",
                                        backgroundPosition: "center"
                                    }}>
                                        <h1 style={{color: banner.textColor, fontFamily: banner.textStyle}}>{banner.type}: {banner.title}</h1>
                                        {banner.text.split('\n').map((line, index) => {
                                            return <div key={index}>
                                                <p className='banner-main-text' style={{color: banner.textColor, fontSize: banner.textSize, fontFamily: banner.textStyle}}>{line}</p>
                                            </div>
                                        })}
                                    </div>
                                }  
                            </div>
                        })}
                    </Carousel>
                }
            </div>
            
            <h1 style={{width: "100%", textAlign: "center"}}> Жанры </h1>

            <ul className='genreSlider'>
                {genres.length > 0 &&
                    genres.sort((a, b) => a.id - b.id).map((genre) => {
                        return <li key={genre.id} style={{listStyle: "none"}}><button onClick={() => {navigation(`shop/genre/${genre.id}`)}} className='genreSliderItem' style={{
                            backgroundColor:genre.color,
                            backgroundImage: genre.backgroundLocation ? `url(${getShopMaterialsImage + genre.backgroundLocation})` : "none",
                            fontFamily:genre.textStyle,
                            color: genre.textColor}}>{genre.name}</button></li>
                    })
                }
            </ul>

            <BookFinder />
        </div>
    )
}