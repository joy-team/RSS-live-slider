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

사용자의 화면에 나타나고 있는 피드를 자동으로 재생합니다. 5초 간격으로 사진/동영상이 보여지며 한 게시글의 컨텐츠가 모두 재생되면 다음 게시글로 넘어갑니다. 마지막 게시글까지 모두 재생된 후에는 다시 첫번째 게시글로 돌아옵니다.

재생 중인 컨텐츠는 하단 tab의 하얀 테두리를 통해 표시되며 tab을 통해 재생할 컨텐츠를 선택할 수 도 있습니다.

<div>
<center>
<img src="https://user-images.githubusercontent.com/37107066/62205027-71c44f80-b3c9-11e9-9d9c-22bdd0f975eb.gif" width="40%" height="40%">
<img src="https://user-images.githubusercontent.com/37107066/62205028-71c44f80-b3c9-11e9-88fe-cb195f99b7ce.gif" width="40%" height="40%">
</center>
</div>

### - 검색

해당 키워드를 포함한 게시글의 피드를 보여 줍니다.

<div>
<center>
<img src="https://user-images.githubusercontent.com/37107066/62205030-725ce600-b3c9-11e9-9990-93199dd2eac9.gif" width="40%" height="40%">
</center>
</div>

### - 새로고침

스와이프를 통해 새로고침이 가능하며 새로고침을 통해 피드를 업데이트할 수 있습니다. 검색 결과 창에서 새로고침 할 경우 본래의 피드 목록으로 돌아옵니다.

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
