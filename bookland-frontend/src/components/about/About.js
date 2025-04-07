import React from 'react'
import Image1 from '../../img/about/Print.jpg'
import '../../index.css'

const Image = () => {
    return (
        <img src={Image1} style={{ maxWidth: 425, maxHeight: 375, minWidth: 225, minHeight: 175, margin: 10 }}   />
        )
}

export const About = () => {
    return (
        <>
            <div className="titleTextStyle">
                <h1 >Bookland</h1>
            </div>

            <div className="blockText">
                <div>
                    <p className="text">Издательство «Bookland» занимается выпуском книг в различных жанрах.
                        Издательство открылось в 2000 году и изначально занималось дистрибьюцией книг.
                        В 2006 году компания начала осуществлять издательскую деятельность, а в следующем
                        году открыло и собственное производство.</p>
                    <p className="text">
                        Сегодня издательство «Bookland» имеет две редакции:
                    </p>
                    <ol className="text">
                        <li>Редакция художественных произведений занимается выпуском классических произведений,
                            прозы, фэнтези и научной фантастики. Это самое крупное подразделение компании.</li>
                        <li>Редакция научной литературы. Это подразделение занимается выпуском научных трудов,
                            учебной литературы, а также пособий в таких сферах как: физика, химия, космические науки, бизнес,
                            информационные технологии и другие.</li>
                    </ol>
                </div>
            </div>
            
            <div className="blockText">
                <Image />
                <div style={{ marginLeft: "10px" }}>
                    <p className="text">У нас есть 2 типографии: в Пскове и Санкт-Петербурге.
                        Типографии могут распечатать ваши:</p>
                    <ol className="text">
                        <li>Книги</li>
                        <li>Журналы</li>
                        <li>Учебные материалы</li>
                        <li>Брошюры</li>
                        <li>фотографии</li>
                        <li>Документы</li>
                        <li>И монгое другое</li>
                    </ol>
                    <a className="text">Узнать подробнее</a>
                </div>
            </div>
        </>
        )
}