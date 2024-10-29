import React, { useState } from 'react'
import { Button, Input, Space } from 'antd';
import { RegUser } from '../../api/AuthApiMethods';
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

    const [login, setLogin] = useState('');
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [password2, setPassword2] = useState('');

    const [image, setImage] = useState('');
    const [dragActive, setDragActive] = useState(false);

    const AddUserClick = async () => {
        setError('')
        if (login !== "" && password !== "" && password2 !== "") {
            if (password === password2) {
                let messsage;
                await RegUser(login, password, name, null, image).then(res => {
                    messsage = res
                })
                if (messsage.status >= 400){
                    setError(messsage.data)
                } else {
                    navigator('/login')
                }
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

    const handleChange = (e) => {
        e.preventDefault();
        if (e.target.files[0]){
            setImage(e.target.files[0])
        }
    }

    const dragHandler = (e) => {
        e.preventDefault();
        setDragActive(true);
    }

    const leaveHandler = (e) => {
        e.preventDefault();
        setDragActive(false);
    }

    const imageDrop = (e) => {
        e.preventDefault();
        setDragActive(false);
        if (e.dataTransfer.files[0]){
            setImage(e.dataTransfer.files[0])
        }
    }

    return (
        <div style={{ display: "flex", justifyContent: "center", marginTop: "2vh" }}>
            <div className="mainBlockStyle">
                    <input id='inputImage' type="file" accept='image/*' multiple={false} onChange={handleChange} hidden/>
                    <Space direction="vertical">
                        <h1 style={{ color: "#7146bd", textAlign: "center"}}>Регистрация</h1>
                        <Space direction="horizontal">
                            <Space direction="vertical">
                                <div 
                                    className={`imageField ${dragActive ? "Drag" : ""}`}
                                    onDragEnter={dragHandler}
                                    onDragOver={dragHandler}
                                    onDragLeave={leaveHandler}
                                    onDrop={imageDrop}
                                >
                                {image?
                                    <img className='imageField' src={URL.createObjectURL(image)}/>
                                    :
                                        <p>Перетащите картинку сюда</p>
                                }
                                </div>
                                <Button className='addImageButton' onClick={() => {document.getElementById('inputImage').click()}}>Загрузить</Button>
                            </Space>
                            <Space direction="vertical">
                                <Input status={LoginStatus} placeholder={LoginText} value={login} onChange={(e) => LoginChange(e)} />
                                <Input placeholder="Имя пользователя" value={name} onChange={(e) => NameChange(e)}/>
                                <Input.Password status={PasswordStatus} placeholder={PasswordText} value={password} onChange={(e) => PasswordChange(e)} />
                                <Input.Password status={PasswordStatus2} placeholder={PasswordText2} value={password2} onChange={(e) => PasswordChange2(e)} />
                                <Space direction="horizontal" style={{ display: 'flex', justifyContent: 'flex-end' }}>
                                    <Button onClick={() => navigator('/login')}>Вход</Button>
                                    <Button id='RegButton' type="primary" onClick={() => AddUserClick()}>Регистрация</Button>
                                </Space>
                            </Space>
                        </Space>
                        
                        <p style={{ color: "red", display: "flex", justifyContent: "center" }}>{error}</p>
                    </Space>
            </div>
        </div>
    )
}