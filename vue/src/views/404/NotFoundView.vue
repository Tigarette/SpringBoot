<template>
    <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
        <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
            <path
                d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
        </symbol>
    </svg>
    <div class="row" style="width: 100%; margin-top: 30px;">
        <div class="col-3"></div>
        <div class="col-6">
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <div>
                    <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Danger:">
                        <use xlink:href="#exclamation-triangle-fill" />
                    </svg>
                    <br />
                    该页面找不到
                    <hr>
                    <h1 class="text-center">404</h1>
                    <h2 class="text-center">NOT FOUND</h2>
                    <hr>
                    <button style="float: right;">
                        返回主页
                    </button>
                </div>
            </div>
        </div>
        <div class="col-3"></div>
    </div>
</template>
<style scoped>
hr {
    width: 100%;
}
</style>