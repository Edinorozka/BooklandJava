import React from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import '../../index.css'
import { BookFinder } from '../Finders/BookFinder';

export const SearchBookShop = () =>{
    const { search } = useParams();

    return (
        <div className='mainBodyStyle' >
            <BookFinder inName={search}/>
        </div>
    )
}