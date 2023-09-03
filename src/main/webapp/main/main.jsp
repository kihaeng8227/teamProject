<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>메인화면</title>
<link rel="stylesheet" href="../resources/css/main.css">
</head>
<body>
	<jsp:include page="../inc/header.jsp" />
	<main>

		<div id="myCarousel" class="carousel slide mb-5"
			data-bs-ride="carousel">
			<div class="carousel-indicators">
				<button type="button" data-bs-target="#myCarousel"
					data-bs-slide-to="0" class="active" aria-current="true"
					aria-label="Slide 1"></button>
				<button type="button" data-bs-target="#myCarousel"
					data-bs-slide-to="1" aria-label="Slide 2"></button>
				<button type="button" data-bs-target="#myCarousel"
					data-bs-slide-to="2" aria-label="Slide 3"></button>
			</div>
			<div class="carousel-inner">
				<div class="carousel-item active mainImg1">

					<div class="container">
						<div class="carousel-caption text-start">
							<h1>메인 슬라이드1</h1>
							<p>메인 슬라이드 설명글 입니다.</p>
							<p>
								<a class="btn btn-lg btn-primary" href="#">이벤트 보러가기</a>
							</p>
						</div>
					</div>
				</div>
				<div class="carousel-item mainImg2">

					<div class="container">
						<div class="carousel-caption">
							<!--<h1>메인 슬라이드2</h1>
							 <p>메인슬라이드2 설명</p> 
							<p>
								<a class="btn btn-lg btn-primary" href="#">Learn more</a>
							</p>-->
						</div>
					</div>
				</div>
				<div class="carousel-item mainImg3">

					<div class="container">
						<div class="carousel-caption text-end">
							<!--<h1>One more for good measure.</h1>
							<p>Some representative placeholder content for the third
								slide of this carousel.</p>
							<p>
								<a class="btn btn-lg btn-primary" href="#">Browse gallery</a>
							</p>-->
						</div>
					</div>
				</div>
			</div>
			<button class="carousel-control-prev" type="button"
				data-bs-target="#myCarousel" data-bs-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Previous</span>
			</button>
			<button class="carousel-control-next" type="button"
				data-bs-target="#myCarousel" data-bs-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Next</span>
			</button>
		</div>

	</main>


	<div class="container marketing">
		<div class="row featurette">
			<div class="col-md-7">
				<h2 class="featurette-heading fw-normal lh-1">
					First featurette heading. <span class="text-muted">It’ll
						blow your mind.</span>
				</h2>
				<p class="lead">Some great placeholder content for the first
					featurette here. Imagine some exciting prose here.</p>
			</div>
			<div class="col-md-5 mainImg4"></div>
		</div>

		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-7 order-md-2">
				<h2 class="featurette-heading fw-normal lh-1">
					Oh yeah, it’s that good. <span class="text-muted">See for
						yourself.</span>
				</h2>
				<p class="lead">Another featurette? Of course. More placeholder
					content here to give you an idea of how this layout would work with
					some actual real-world content in place.</p>
			</div>
			<div class="col-md-5 order-md-1 mainImg5">
			</div>
		</div>

		<hr class="featurette-divider">

		<div class="row featurette mb-5">
			<div class="col-md-7">
				<h2 class="featurette-heading fw-normal lh-1">
					And lastly, this one. <span class="text-muted">Checkmate.</span>
				</h2>
				<p class="lead">And yes, this is the last block of
					representative placeholder content. Again, not really intended to
					be actually read, simply here to give you a better view of what
					this would look like with some actual content. Your content.</p>
			</div>
			<div class="col-md-5 mainImg6">
			</div>
		</div>
	</div>

	<jsp:include page="../inc/footer.jsp" />
</body>
</html>