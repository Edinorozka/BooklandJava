import React, { useState, useEffect } from 'react'
import { Button, Input, Space, ConfigProvider } from 'antd';
import { GetUser, Login } from '../../api/AuthApiMethods';
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import '../../index.css'

export const Authorization = () => {
    const navigator = useNavigate();
    const [LoginText, setLoginText] = useState("Логин");
    const [PasswordText, setPasswordText] = useState("Пароль");
    const [LoginStatus, setLoginStatus] = useState('');
    const [PasswordStatus, setPasswordStatus] = useState('');
    const [error, setError] = useState('');
    const dispatch = useDispatch();

    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');

    const { token } = useSelector(state => state.Token);

    const AuthUserClick = async () => {
        setError('')
        if (login !== "" && password !== "") {
            const config = {
                login: login,
                password: password
            }
            const e = await Login(dispatch, config)
            if (!token) {
                setError(e)
            }
        } else {
            if (login === "") {
                setLoginText("Введите логин!")
                setLoginStatus("error")
            }
            if (password === "") {
                setPasswordText("Введите пароль!")
                setPasswordStatus("error")
            }
        }
    }

    useEffect(() => {
        if (token) {
            GetUser(dispatch, token, login);
            navigator('/');
        }
    }, [token, dispatch, login, navigator]);
    
    const LoginChange = (e) => {
        setLogin(e.target.value)
        setLoginText("Логин")
        setLoginStatus("")
    }

    const PasswordChange = (e) => {
        setPassword(e.target.value)
        setPasswordText("Пароль")
        setPasswordStatus("")
    }

    return (
        <div style={{ display: "flex", justifyContent: "center", marginTop: "2vh" }}>
            <div className="mainBlockStyle">
                <ConfigProvider
                    theme={{
                        token: {
                            colorPrimary: '#7146bd',
                            colorBorderSecondary: '#FFFFFF',
                        }
                    }}
                >
                    <Space direction="vertical">
                        <h1 style={{ color: "#7146bd" }}>Авторизация</h1>
                        <Input status={LoginStatus} placeholder={LoginText} value={login} onChange={(e) => LoginChange(e)} />
                        <Input.Password status={PasswordStatus} placeholder={PasswordText} value={password} onChange={(e) => PasswordChange(e)} />
                        <Space direction="horizontal" style={{ display: 'flex', justifyContent: 'flex-end' }}>
                            <Button onClick={() => navigator('/registration')}>Регистрация</Button>
                            <Button type="primary" onClick={() => AuthUserClick()}>Вход</Button>
                        </Space>
                        <p style={{ color: "red", display: "flex", justifyContent: "center" }}>{error}</p>
                    </Space>
                </ConfigProvider>
            </div>
        </div>
        )
    
}
