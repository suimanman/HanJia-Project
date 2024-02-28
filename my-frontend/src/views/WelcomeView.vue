<template>
    <div style="width: 100vw; height: 100vh; overflow: hidden; display: flex">
        <div style="flex: 1; background-color: aquamarine">
            <el-image
                    style="width: 100%; height: 100%"
                    fit="cover"
                    src="https://n.sinaimg.cn/sinacn16/2/w1920h1282/20180328/fb0f-fysqfnh9646112.jpg"
            />
        </div>
        <div class="welcome-title">
            <div style="font-size: 30px; font-weight: bold">项目一</div>
            <div style="margin-top: 10px">
                简单的登录界面，包括验证码登录，密码验证，修改密码，注册等功能。
            </div>
        </div>
        <div class="right-card"><RouterView /></div>
    </div>
</template>

<script lang="ts" setup name="Welcome"></script>

<style scoped>
.welcome-title {
    position: absolute;
    bottom: 30px;
    left: 30px;
    color: white;
    text-shadow: 0 0 10px black;
}
.right-card {
    width: 400px;
    z-index: 1;
    background-color: white;
}
</style>
