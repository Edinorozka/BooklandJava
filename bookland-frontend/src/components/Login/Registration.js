import React, { useState } from 'react'
import { Button, Input, Space, ConfigProvider} from 'antd';
import { RegUser } from '../../api/AuthApiMethods';
import { useDispatch} from "react-redux";
import { useNavigate } from "react-router-dom";
import '../../index.css'

export const Registration = () => {
    const navigator = useNavigate();
    const [LoginText, setLoginText] = useState("Логин");
    const [PasswordText, setPasswordText] = useState("Пароль");
    const [PasswordText2, setPasswordText2] = useState("Повторите пароль");
    const [LoginStatus, setLoginStatus] = useState('');
    const [PasswordStatus, setPasswordStatus] = useState('');
    const [PasswordStatus2, setPasswordStatus2] = useState('');
    const [error, setError] = useState('');
    const dispatch = useDispatch();

    const [login, setLogin] = useState('');
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [password2, setPassword2] = useState('');

    const AddUserClick = async () => {
        setError('')
        if (login !== "" && password !== "" && password2 !== "") {
            if (password == password2) {
                var user = null
                if (name == '') {
                    user = {
                        login: login,
                        password: password,
                    }
                } else {
                    user = {
                        login: login,
                        password: password,
                        name: name,
                    }
                }
                await RegUser(dispatch, user)
                navigator('/login')
            } else {
                setPasswordStatus("error")
                setPasswordStatus2("error")
                setError("Пароли не совподают")
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
            if (password2 === "") {
                setPasswordText2("Повторите пароль!")
                setPasswordStatus2("error")
            }
        }
    }

    const LoginChange = (e) => {
        setLogin(e.target.value)
        setLoginText("Логин")
        setLoginStatus("")
    }

    const NameChange = (e) => {
        setName(e.target.value)
    }

    const PasswordChange = (e) => {
        setPassword(e.target.value)
        setPasswordText("Пароль")
        setPasswordStatus("")
    }

    const PasswordChange2 = (e) => {
        setPassword2(e.target.value)
        setPasswordText2("Повторите пароль")
        setPasswordStatus2("")
    }


    return (
        <div style={{ display: "flex", justifyContent: "center", marginTop: "2%" }}>
            <div className="blockAuthAndReg">
                <ConfigProvider
                    theme={{
                        token: {
                            colorPrimary: '#7146bd',
                            colorBorderSecondary: '#FFFFFF',
                        }
                    }}
                >
                    <Space direction="vertical">
                        <h1 style={{ color: "#7146bd" }}>Регистрация</h1>
                        <Input status={LoginStatus} placeholder={LoginText} value={login} onChange={(e) => LoginChange(e)} />
                        <Input placeholder="Имя пользователя" value={name} onChange={(e) => NameChange(e)}/>
                        <Input.Password status={PasswordStatus} placeholder={PasswordText} value={password} onChange={(e) => PasswordChange(e)} />
                        <Input.Password status={PasswordStatus2} placeholder={PasswordText2} value={password2} onChange={(e) => PasswordChange2(e)} />
                        <Space direction="horizontal" style={{ display: 'flex', justifyContent: 'flex-end' }}>
                            <Button onClick={() => navigator('/login')}>Вход</Button>
                            <Button type="primary" onClick={() => AddUserClick()}>Регистрация</Button>
                        </Space>
                        <p style={{ color: "red" }}>{error}</p>
                    </Space>
                </ConfigProvider>
            </div>
        </div>
    )
}