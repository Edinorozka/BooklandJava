import React, {useState} from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { Menu, Avatar, Button, Dropdown, Select } from 'antd'
import { SearchOutlined } from '@ant-design/icons';
import { useDispatch, useSelector } from "react-redux";
import { UserOutlined } from '@ant-design/icons'
import { deleteToken } from "../../store/reducers/TokenSlice"
import { deleteUser } from "../../store/reducers/UserSlice"
import { getIconUrl } from "../Urls";
import '../../index.css'
import { Logout } from '../../api/AuthApiMethods';
import { Finder } from '../Finders/FindByText';


export const Navbar = (props) => {
    const navigation = useNavigate()
    const { id, name, icon, userLogged } = useSelector(state => state.user);
    const { token } = useSelector(state => state.Token);
    const dispatch = useDispatch();

    React.useEffect(() => {
        
    }, [name, userLogged])

    const mainNavItems = [
        {
            key: 1,
            label: <Link to="/" >Магазин</Link>
        },
        {
            key: 2,
            label: <Link to="/blog" >Книжный клуб</Link>
        },
        {
            key: 3,
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
            label: 'Корзина',
            onClick: () => {
                navigation(`/cart`)
            }
        },
        {
            key: '3',
            label: 'Заказы',
            onClick: () => {
                navigation(`/cart`)
            }
        },
        {
            key: '4',
            danger: true,
            label: 'Выход',
            onClick: async () => {
                await Logout(token, id)
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
            <div style={{ background: '#FFFFFF', position: "fixed", top: "0", zIndex: "1000", width: "100%"}}>
                    <nav className="navbarMainStyle" style={{ maxHeight: "50", border: "20px" }}>
                        <a href='/' style={{textDecoration: "none", color: "black"}}>
                            <h1 style={{ marginTop: 1 }}>Bookland</h1>
                        </a>
                        <div style={{  }}>
                            <Menu mode="horizontal" items={mainNavItems} style={{ fontSize: '25px' }} />
                        </div>
                        <div style={{ display: 'flex', alignItems: 'center', width: "100%" }}>
                        
      <Finder/>
    </div>

                        <div style={{ width: "100%", display: 'flex', justifyContent: "flex-end"}}>
                        {userLogged &&
                            <>
                            <p className={ "nameStyle" }>{name}</p>
                                <Dropdown
                                menu={{ items: commandsItems, }}
                                
                            >
                                {icon? 
                                    <Avatar size={45} style={{ marginLeft: "10px" }} src={getIconUrl + icon} className="navbarAvatarStyle" onClick={(e) => e.preventDefault()} />
                                : 
                                    <Avatar size={45} style={{ marginLeft: "10px" }} icon={<UserOutlined />} className="navbarAvatarStyle" onClick={(e) => e.preventDefault()} />
                                }
                                </Dropdown>    
                            </>
                            
                        }

                        {!userLogged &&
                            <>
                            <Button type="primary" style={{ right: '15px', top: '20%' }} onClick={() => LoginClick()}>Войти</Button>
                            <Button style={{ right: '10px', top: '20%' }} onClick={() => { navigation('/registration') }}>Регистрация</Button>
                                <Avatar size={45} icon={<UserOutlined />} className="navbarAvatarStyle" />
                            </>
                        }
                        </div>
                        
                    </nav>
            </div>
        <div className="mainPartStyle">
            {props.children}
        </div>
        </>
        
    )
}