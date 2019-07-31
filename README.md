# Live Slider for Instagram Feed :star:

본 프로젝트는 관심 사용자의 RSS를 제공받아 Instagram 게시글을 한눈에 확인할 수 있도록 피드해주는 어플리케이션입니다.

## RSS 컨텐츠

Instagram의 RSS는 `http://fetchrss.com` 를 통해 생성하여 사용하였으며 이로부터 얻은 RSS에서 사용가능한 데이터는 다음과 같습니다.
```
- 관심 사용자 아이디
- 새 게시글 업로드 날짜
- 게시글 내용
```

또한 게시글 내용은 다음과 같은 유형들로 분류 가능합니다.
```
- 텍스트
- 다수의 사진
- 단일 동영상
```

#### 컨텐츠의 한계
해당 사이트 `http://fetchrss.com` 로부터 생성한 RSS에서 제공되는 ___이미지/비디오 URL 시간의 지남에 따라 만료되는 상황이 발생___ 하여 일부 컨텐츠가 로드되지 않는 현상이 나타납니다.


## 구현 사항
### - 슬라이더 자동 재생
<div>
<center>
<img src="https://user-images.githubusercontent.com/37107066/62205027-71c44f80-b3c9-11e9-9d9c-22bdd0f975eb.gif" width="40%" height="40%">
<img src="https://user-images.githubusercontent.com/37107066/62205028-71c44f80-b3c9-11e9-88fe-cb195f99b7ce.gif" width="40%" height="40%">
</center>
</div>

### - 검색
<div>
<center>
<img src="https://user-images.githubusercontent.com/37107066/62205030-725ce600-b3c9-11e9-9990-93199dd2eac9.gif" width="40%" height="40%">
</center>
</div>

### - 새로고침
<div>
<center>
<img src="https://user-images.githubusercontent.com/37107066/62205029-71c44f80-b3c9-11e9-8a77-99713e81868e.gif" width="40%" height="40%">
</center>
</div>

## 사용 라이브러리
```
- https://github.com/square/retrofit
- https://github.com/JakeWharton/butterknife
- https://github.com/Tickaroo/tikxml
- https://github.com/square/okhttp
- https://github.com/bumptech/glide
- https://github.com/google/ExoPlayer
- https://github.com/facebook/shimmer-android
```
