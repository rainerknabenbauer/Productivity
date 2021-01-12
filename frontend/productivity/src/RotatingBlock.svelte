<script>

</script>

<style>
.btn-flip {
  opacity: 1;
  outline: 0;
  color: #fff;
  line-height: 40px;
  position: relative;
  text-align: center;
  letter-spacing: 1px;
  display: inline-block;
  text-decoration: none;
  font-family: 'Open Sans';
  text-transform: uppercase;
  min-width: 132pt;
  border: 2px solid transparent;
  border-radius: 25px;
}
.btn-flip:hover:after {
  opacity: 1;
  transform: translateY(0) rotateX(0);
}
.btn-flip:hover:before {
  opacity: 0;
  transform: translateY(50%) rotateX(90deg);
}
.btn-flip:after {
  top: 0;
  left: 0;
  opacity: 0;
  width: 100%;
  color: #323237;
  display: block;
  transition: 0.5s;
  position: absolute;
  background: #d3d3d3;
  content: attr(data-back);
  transform: translateY(-50%) rotateX(90deg);
}
.btn-flip:before {
  top: 0;
  left: 0;
  opacity: 1;
  color:  #323237;
  display: block;
  padding: 0 30px;
  line-height: 40px;
  transition: 0.5s;
  position: relative;
  background: #adadaf;
  content: attr(data-front);
  transform: translateY(0) rotateX(0);
}

</style>



<a href="#" class="btn-flip w3-margin-left" data-front="Add note" data-back="&#x2794;" on:click></a>

