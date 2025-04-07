import React from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import '../../index.css'
import { BookFinder } from '../Finders/BookFinder';

export const GenreShop = () =>{
    const { genre_id } = useParams();

    return (
        <div className='mainBodyStyle' >
            <BookFinder genreId={genre_id}/>
        </div>
    )
}