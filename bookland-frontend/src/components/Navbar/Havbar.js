import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { Menu,  Avatar, Button, Dropdown } from 'antd'
import { useDispatch, useSelector } from "react-redux";
import { UserOutlined } from '@ant-design/icons'
import { deleteToken } from "../../store/reducers/TokenSlice"
import { deleteUser } from "../../store/reducers/UserSlice"
import { getIconUrl } from "../Urls";
import '../../index.css'


export const Navbar = (props) => {
    const navigation = useNavigate()
    const { name, icon, userLogged } = useSelector(state => state.user);
    const dispatch = useDispatch();

    React.useEffect(() => {
        console.log("Ваше имя " + name)
        console.log(userLogged)
    }, [name, userLogged])

    const mainNavItems = [
        {
            label: <Link to="/" >Магазин</Link>
        },
        {
            label: <Link to="/blog" >Блог</Link>
        },
        {
            label: <Link to="/about" >О нас</Link>
        },
    ]

    const commandsItems = [
        {
            key: '1',
            label: 'Параметры',
            onClick: () => {
                navigation(`/settings`)
            }
        },
        {
            key: '2',
            label: 'Покупки',
            onClick: () => {
                navigation(`/cart`)
            }
        },
        {
            key: '3',
            danger: true,
            label: 'Выход',
            onClick: () => {
                dispatch(deleteToken())
                dispatch(deleteUser())
            },
        },
    ];

    const LoginClick = () => {
        navigation(`/login`)
    }

    return (
        <>
            <div style={{ background: '#FFFFFF' }}>
                    <nav className="navbarMainStyle" style={{ maxHeight: "50", border: "20px" }}>
                        <h1 style={{ marginTop: 1 }}>Bookland</h1>
                        <div style={{ width: '100%' }}>
                            <Menu mode="horizontal" items={mainNavItems} style={{ fontSize: '25px' }} />
                        </div>

                        {userLogged &&
                            <div style={{ display: 'flex'}}>
                            <p className={ "nameStyle" }>{name}</p>
                                <Dropdown
                                menu={{ items: commandsItems, }}
                                
                            >
                                {icon? 
                                    <Avatar size={45} style={{ left: "10%" }} src={getIconUrl + icon} className="navbarAvatarStyle" onClick={(e) => e.preventDefault()} />
                                : 
                                    <Avatar size={45} style={{ left: "10%" }} icon={<UserOutlined />} className="navbarAvatarStyle" onClick={(e) => e.preventDefault()} />
                                }
                                </Dropdown>    
                            </div>
                            
                        }

                        {!userLogged &&
                            <>
                            <Button type="primary" style={{ float: 'right', right: '15px', top: '20%' }} onClick={() => LoginClick()}>Войти</Button>
                            <Button style={{ float: 'right', right: '10px', top: '20%' }} onClick={() => { navigation('/registration') }}>Регистрация</Button>
                                <Avatar size={45} icon={<UserOutlined />} className="navbarAvatarStyle" />
                            </>
                        }

                        
                    </nav>
        </div>
        <div className="mainPartStyle">
            {props.children}
        </div>
        </>
        
    )
}