import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from "react-redux";
import { Button, Space, Select, Card, Skeleton, Pagination, Avatar, Carousel, Flex, Radio } from 'antd';
import '../../index.css'
import { BookFinder } from '../Finders/BookFinder';

export const Shop = () =>{
    return (
        <div className='mainBodyStyle' >
            <BookFinder />
        </div>
    )
}