import $ from 'jquery'
import store from '@/store'

let base = window.location.protocol + '//' + window.location.hostname + ":3000";
export const postRequest = (url, params, success, error) => {
        return $.ajax({
            url: base + url,
            data: params,
            type: "post",
            headers: {
                Authorization: "Bearer " + store.state.user.token,
            },
            success,
            error,
        });
    }
    // export const uploadFileRequest = (url, params) => {
    //     return axios({
    //         method: 'post',
    //         url: `${base}${url}`,
    //         data: params,
    //         headers: {
    //             'Content-Type': 'multipart/form-data',
    //         }
    //     });
    // }
    // export const putRequest = (url, params) => {
    //     return axios({
    //         method: 'put',
    //         url: `${base}${url}`,
    //         data: params,
    //         transformRequest: [function(data) {
    //             let ret = ''
    //             for (let it in data) {
    //                 ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
    //             }
    //             return ret
    //         }],
    //         headers: {
    //             'Content-Type': 'application/x-www-form-urlencoded',
    //         }
    //     });
    // }
    // export const deleteRequest = (url) => {
    //     return axios({
    //         method: 'delete',
    //         url: `${base}${url}`,
    //     });
    // }
    // export const getRequest = (url, params) => {
    //     return axios({
    //         method: 'get',
    //         data: params,
    //         url: `${base}${url}`,
    //         transformRequest: [function(data) {
    //             let ret = ''
    //             for (let it in data) {
    //                 ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
    //             }
    //             return ret
    //         }],
    //         headers: {
    //             'Content-Type': 'application/x-www-form-urlencoded',
    //         },
    //     });
    // }

// export const getUserinfo = () => {
//     return axios({
//         method: 'get',
//         url: base + '/user/getInfo',
//     })
// }

// export const hasAuthority = (arr, me) => {
//     if (arr === null || arr.length === 0)
//         return false;
//     for (let i in arr) {
//         // console.log(val[i].authority)
//         if (arr[i].authority === me)
//             return true;
//     }
//     return false;
// }