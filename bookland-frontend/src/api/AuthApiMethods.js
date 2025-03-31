import axios from "axios"
import { authUrl, getUserUrl, refreshTokenUrl, createUrl, getIconUrl, logoutUser } from "../components/Urls";
import { getToken, deleteToken } from "../store/reducers/TokenSlice"
import { getUser, deleteUser } from "../store/reducers/UserSlice"

export const Login = async (dispatch, config) => {
    try {
        await axios.post(authUrl, config).then(res => {
            dispatch(getToken(res.data))
        });
    } catch (e) {
        return e.response.data
    }
}

export async function GetUser(dispatch, token, login) {
    try {
        await axios.get(getUserUrl + login, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                dispatch(getUser(res.data))
            })
    } catch (e) {
        console.log(e)
    }
}

export const RegUser = async (login, password, name, role, icon) => {
    try {
        var bodyData = new FormData();
        bodyData.append("login", login)
        bodyData.append("password", password)
        if (name !== "") bodyData.append("name", name)
        if (role !== null) bodyData.append("role", role)
        if (icon !== null) bodyData.append("icon", icon)

        return await axios({
            method: "post",
            url: createUrl,
            headers: { 'Content-Type': 'multipart/form-data' },
            data: bodyData,
        })
    } catch (e) {
        return e.response
    }
}

export async function RefreshToken(dispatch, refreshToken){
    try {
        await axios.post(refreshTokenUrl, { refreshToken }).then(res => {
            dispatch(getToken(res.data))
        })
    } catch (e) {
        console.log(e)
    }
}

export async function GetIcon(dispatch, icon){
    try {
        await axios.get(getIconUrl + icon).then(res => {
            return res.data
        })
    } catch (e) {
        console.log(e)
    }
}

export async function Logout(token, userId) {
    try {
        await axios.get(logoutUser + userId, { headers: { Authorization: `Bearer ${token}` } })
    } catch (e) {
        console.log(e)
    }
}