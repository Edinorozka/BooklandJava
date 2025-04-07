import React from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import '../../index.css'
import { BookFinder } from '../Finders/BookFinder';

export const AuthorShop = () =>{
    const { author_id } = useParams();

    return (
        <div className='mainBodyStyle' >
            <BookFinder authorId={author_id}/>
        </div>
    )
}