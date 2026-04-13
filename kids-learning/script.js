/* ============================================================
   FunLearn – JavaScript
   Modules: Navigation | Alphabets | Numbers | Colors | Shapes
            | Rhymes | Quiz | Sound FX | Confetti | localStorage
   ============================================================ */
'use strict';

/* ── DATA ─────────────────────────────────────────────────── */

const ALPHABETS = [
  { l:'A', w:'Apple',      hi:'अ से सेब',       img:'https://images.unsplash.com/photo-1568702846914-96b305d2aaeb?w=300&q=80' },
  { l:'B', w:'Ball',       hi:'ब से गेंद',      img:'https://images.unsplash.com/photo-1617155093730-a8bf47be792d?w=300&q=80' },
  { l:'C', w:'Cat',        hi:'क से बिल्ली',    img:'https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=300&q=80' },
  { l:'D', w:'Dog',        hi:'ड से कुत्ता',    img:'https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=300&q=80' },
  { l:'E', w:'Elephant',   hi:'ए से हाथी',      img:'https://images.unsplash.com/photo-1557050543-4d5f4e07ef46?w=300&q=80' },
  { l:'F', w:'Fish',       hi:'फ से मछली',      img:'https://images.unsplash.com/photo-1522069169874-c58ec4b76be5?w=300&q=80' },
  { l:'G', w:'Grapes',     hi:'ग से अंगूर',     img:'https://images.unsplash.com/photo-1537640538966-79f369143f8f?w=300&q=80' },
  { l:'H', w:'Horse',      hi:'ह से घोड़ा',     img:'https://images.unsplash.com/photo-1534773728080-33d31da27ae5?w=300&q=80' },
  { l:'I', w:'Igloo',      hi:'इ से इग्लू',     img:'https://images.unsplash.com/photo-1548535535-7d8c4fca6dbc?w=300&q=80' },
  { l:'J', w:'Jellyfish',  hi:'ज से जेलिफ़िश',  img:'https://images.unsplash.com/photo-1596460107916-430662021049?w=300&q=80' },
  { l:'K', w:'Kite',       hi:'क से पतंग',      img:'https://images.unsplash.com/photo-1601758003122-53c40e686a19?w=300&q=80' },
  { l:'L', w:'Lion',       hi:'ल से शेर',       img:'https://images.unsplash.com/photo-1546182990-dffeafbe841d?w=300&q=80' },
  { l:'M', w:'Mango',      hi:'म से आम',        img:'https://images.unsplash.com/photo-1553279768-865429fa0078?w=300&q=80' },
  { l:'N', w:'Nest',       hi:'न से घोंसला',    img:'https://images.unsplash.com/photo-1444464666168-49d633b86797?w=300&q=80' },
  { l:'O', w:'Orange',     hi:'ओ से संतरा',     img:'https://images.unsplash.com/photo-1547514701-42782101795e?w=300&q=80' },
  { l:'P', w:'Parrot',     hi:'प से तोता',      img:'https://images.unsplash.com/photo-1552728089-57bdde30beb3?w=300&q=80' },
  { l:'Q', w:'Queen',      hi:'क्व से रानी',    img:'https://images.unsplash.com/photo-1580927752452-89d86da3fa0a?w=300&q=80' },
  { l:'R', w:'Rabbit',     hi:'र से खरगोश',     img:'https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?w=300&q=80' },
  { l:'S', w:'Sun',        hi:'स से सूरज',      img:'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=300&q=80' },
  { l:'T', w:'Tiger',      hi:'ट से बाघ',       img:'https://images.unsplash.com/photo-1535338454770-8be927b5a00b?w=300&q=80' },
  { l:'U', w:'Umbrella',   hi:'उ से छाता',      img:'https://images.unsplash.com/photo-1518199266791-5375a83190b7?w=300&q=80' },
  { l:'V', w:'Volcano',    hi:'व से ज्वालामुखी',img:'https://images.unsplash.com/photo-1553949285-1196f0a29d22?w=300&q=80' },
  { l:'W', w:'Watermelon', hi:'व से तरबूज',     img:'https://images.unsplash.com/photo-1568584711271-6c929fb49b60?w=300&q=80' },
  { l:'X', w:'Xylophone',  hi:'ज़ से जाइलोफ़ोन',img:'https://images.unsplash.com/photo-1558098329-a11cff621064?w=300&q=80' },
  { l:'Y', w:'Yacht',      hi:'य से नौका',      img:'https://images.unsplash.com/photo-1567899378494-47b22a2ae96a?w=300&q=80' },
  { l:'Z', w:'Zebra',      hi:'ज़ से ज़ेबरा',    img:'https://images.unsplash.com/photo-1526095179574-86e545346ae6?w=300&q=80' },
];

const NUMBERS = [
  {n:1, w:'One',       e:'🍎'},{n:2, w:'Two',       e:'⭐'},
  {n:3, w:'Three',     e:'🌸'},{n:4, w:'Four',      e:'🐢'},
  {n:5, w:'Five',      e:'🦋'},{n:6, w:'Six',       e:'🍓'},
  {n:7, w:'Seven',     e:'🌈'},{n:8, w:'Eight',     e:'🐝'},
  {n:9, w:'Nine',      e:'🎈'},{n:10,w:'Ten',       e:'🐠'},
  {n:11,w:'Eleven',    e:'🌻'},{n:12,w:'Twelve',    e:'🍦'},
  {n:13,w:'Thirteen',  e:'🦄'},{n:14,w:'Fourteen',  e:'🍄'},
  {n:15,w:'Fifteen',   e:'🐥'},{n:16,w:'Sixteen',   e:'🌺'},
  {n:17,w:'Seventeen', e:'🍇'},{n:18,w:'Eighteen',  e:'🐸'},
  {n:19,w:'Nineteen',  e:'🦊'},{n:20,w:'Twenty',    e:'🎉'},
];

const COLORS = [
  {name:'Red',    hex:'#FF4444', example:'🍎 Apple'},
  {name:'Blue',   hex:'#3399FF', example:'🌊 Ocean'},
  {name:'Green',  hex:'#44BB44', example:'🌿 Leaf'},
  {name:'Yellow', hex:'#FFD700', example:'🌞 Sun'},
  {name:'Orange', hex:'#FF8C00', example:'🍊 Orange'},
  {name:'Purple', hex:'#9B59B6', example:'🍇 Grapes'},
  {name:'Pink',   hex:'#FF69B4', example:'🌸 Flower'},
  {name:'Brown',  hex:'#8B4513', example:'🍫 Chocolate'},
  {name:'White',  hex:'#E8E8E8', example:'☁️ Cloud'},
  {name:'Black',  hex:'#222222', example:'🐦 Crow'},
];

const SHAPES = [
  { name:'Circle',    desc:'Round with no corners!',
    svg:`<svg viewBox="0 0 100 100"><circle cx="50" cy="50" r="42" fill="#4D96FF"/><circle cx="50" cy="50" r="42" fill="none" stroke="#fff" stroke-width="4" opacity=".3"/></svg>` },
  { name:'Square',    desc:'4 equal sides!',
    svg:`<svg viewBox="0 0 100 100"><rect x="10" y="10" width="80" height="80" rx="8" fill="#FF6B6B"/><rect x="10" y="10" width="80" height="80" rx="8" fill="none" stroke="#fff" stroke-width="4" opacity=".3"/></svg>` },
  { name:'Triangle',  desc:'3 sides and 3 corners!',
    svg:`<svg viewBox="0 0 100 100"><polygon points="50,8 94,90 6,90" fill="#6BCB77"/><polygon points="50,8 94,90 6,90" fill="none" stroke="#fff" stroke-width="4" opacity=".3"/></svg>` },
  { name:'Rectangle', desc:'2 long sides + 2 short sides!',
    svg:`<svg viewBox="0 0 100 100"><rect x="5" y="24" width="90" height="52" rx="8" fill="#FFD93D"/><rect x="5" y="24" width="90" height="52" rx="8" fill="none" stroke="#fff" stroke-width="4" opacity=".3"/></svg>` },
  { name:'Star',      desc:'5 shiny points!',
    svg:`<svg viewBox="0 0 100 100"><polygon points="50,5 61,35 95,35 68,57 79,91 50,70 21,91 32,57 5,35 39,35" fill="#FF9F43"/><polygon points="50,5 61,35 95,35 68,57 79,91 50,70 21,91 32,57 5,35 39,35" fill="none" stroke="#fff" stroke-width="3" opacity=".3"/></svg>` },
  { name:'Oval',      desc:'Like a stretched circle!',
    svg:`<svg viewBox="0 0 100 100"><ellipse cx="50" cy="50" rx="46" ry="28" fill="#9B59B6"/><ellipse cx="50" cy="50" rx="46" ry="28" fill="none" stroke="#fff" stroke-width="4" opacity=".3"/></svg>` },
  { name:'Diamond',   desc:'A square standing on its tip!',
    svg:`<svg viewBox="0 0 100 100"><polygon points="50,5 95,50 50,95 5,50" fill="#00B4D8"/><polygon points="50,5 95,50 50,95 5,50" fill="none" stroke="#fff" stroke-width="4" opacity=".3"/></svg>` },
  { name:'Heart',     desc:'The love shape!',
    svg:`<svg viewBox="0 0 100 100"><path d="M50 85 C50 85 10 58 10 32 C10 16 22 8 35 12 C42 15 50 22 50 22 C50 22 58 15 65 12 C78 8 90 16 90 32 C90 58 50 85 50 85Z" fill="#FF78C4"/></svg>` },
];

const RHYMES = [
  { title:'Twinkle Twinkle', art:'⭐', lines:[
    'Twinkle, twinkle, little star,',
    'How I wonder what you are!',
    'Up above the world so high,',
    'Like a diamond in the sky.',
    'Twinkle, twinkle, little star,',
    'How I wonder what you are!']},
  { title:'Baa Baa Black Sheep', art:'🐑', lines:[
    'Baa, baa, black sheep,',
    'Have you any wool?',
    'Yes sir, yes sir,',
    'Three bags full!',
    'One for the master,',
    'One for the dame,',
    'And one for the little boy',
    'Who lives down the lane.']},
  { title:'Humpty Dumpty', art:'🥚', lines:[
    'Humpty Dumpty sat on a wall,',
    'Humpty Dumpty had a great fall.',
    'All the king\'s horses',
    'And all the king\'s men,',
    'Couldn\'t put Humpty together again!']},
  { title:'Johny Johny', art:'👶', lines:[
    'Johny Johny, yes Papa,',
    'Eating sugar? No, Papa.',
    'Telling lies? No, Papa.',
    'Open your mouth!',
    'Ha ha ha!']},
  { title:'Jack and Jill', art:'⛰️', lines:[
    'Jack and Jill went up the hill',
    'To fetch a pail of water.',
    'Jack fell down and broke his crown,',
    'And Jill came tumbling after.']},
];

const TILE_COLORS = ['#FF6B6B','#FF9F43','#FFD93D','#6BCB77','#4D96FF','#9B59B6','#FF78C4','#00B4D8'];

/* ── STATE ─────────────────────────────────────────────────── */
let state = {
  alphaIdx:     0,
  slideRunning: false,
  slideInterval:null,
  rhymeIdx:     0,
  rhymeTimer:   null,
  quiz: { category:null, questions:[], current:0, score:0, answered:false },
};

/* ── LOCAL STORAGE PROGRESS ────────────────────────────────── */
function loadProgress() {
  try { return JSON.parse(localStorage.getItem('funlearn_v2')) || {alpha:[],num:[],color:[],quizBest:0}; }
  catch(e) { return {alpha:[],num:[],color:[],quizBest:0}; }
}
function saveProgress() {
  try { localStorage.setItem('funlearn_v2', JSON.stringify(progress)); } catch(e) {}
}
let progress = loadProgress();

function trackItem(key, idx) {
  if (!progress[key].includes(idx)) { progress[key].push(idx); saveProgress(); updateProgressUI(); }
}
function updateProgressUI() {
  const set = (id, pct) => { const el=document.getElementById(id); if(el) el.style.width=Math.min(100,pct)+'%'; };
  set('prog-alpha', Math.round((progress.alpha.length/26)*100));
  set('prog-num',   Math.round((progress.num.length/20)*100));
  set('prog-color', Math.round((progress.color.length/COLORS.length)*100));
  set('prog-quiz',  progress.quizBest);
}

/* ── WEB SPEECH API ────────────────────────────────────────── */
function speak(text, rate=0.82, pitch=1.15) {
  if (!('speechSynthesis' in window)) return;
  window.speechSynthesis.cancel();
  const u = new SpeechSynthesisUtterance(text);
  u.rate=rate; u.pitch=pitch; u.lang='en-US';
  window.speechSynthesis.speak(u);
}

/* ── OSCILLATOR SOUND EFFECTS (no external files) ──────────── */
let audioCtx;
function getACtx() {
  if (!audioCtx) audioCtx = new (window.AudioContext||window.webkitAudioContext)();
  return audioCtx;
}
function beep(freq, type='sine', dur=0.12, vol=0.25) {
  try {
    const ctx=getACtx(), osc=ctx.createOscillator(), g=ctx.createGain();
    osc.type=type; osc.frequency.value=freq;
    osc.connect(g); g.connect(ctx.destination);
    g.gain.setValueAtTime(vol, ctx.currentTime);
    g.gain.exponentialRampToValueAtTime(0.001, ctx.currentTime+dur);
    osc.start(); osc.stop(ctx.currentTime+dur);
  } catch(e){}
}
const sfx = {
  click:   ()=>beep(480,'sine',0.09,0.18),
  pop:     ()=>{ beep(600,'sine',0.07,0.15); setTimeout(()=>beep(800,'sine',0.07,0.12),60); },
  correct: ()=>{ beep(523,'sine',0.12); setTimeout(()=>beep(659,'sine',0.12),110); setTimeout(()=>beep(784,'sine',0.22),220); },
  wrong:   ()=>{ beep(280,'sawtooth',0.12,0.2); setTimeout(()=>beep(240,'sawtooth',0.18,0.2),130); },
};

/* ── NAVIGATION ────────────────────────────────────────────── */
function navigate(section) {
  stopRhyme();
  if (state.slideRunning) toggleSlideshow();
  document.querySelectorAll('.page').forEach(p=>p.classList.remove('active'));
  document.querySelectorAll('.nav-btn').forEach(b=>b.classList.remove('active'));
  const page=document.getElementById(section);
  if (page) page.classList.add('active');
  document.querySelectorAll(`.nav-btn[data-section="${section}"]`).forEach(b=>b.classList.add('active'));
  document.getElementById('main-nav').classList.remove('open');
  window.scrollTo({top:0,behavior:'smooth'});
  updateProgressUI();
}

// All nav + hero buttons
document.querySelectorAll('[data-section]').forEach(btn=>{
  btn.addEventListener('click',()=>{ sfx.click(); navigate(btn.dataset.section); });
});

// Hamburger
document.getElementById('hamburger').addEventListener('click',()=>{
  sfx.click(); document.getElementById('main-nav').classList.toggle('open');
});

// Dark / Light toggle
document.getElementById('theme-toggle').addEventListener('click',()=>{
  sfx.click();
  const root=document.documentElement;
  const dark=root.getAttribute('data-theme')==='dark';
  root.setAttribute('data-theme', dark?'light':'dark');
  document.getElementById('theme-toggle').innerHTML=dark?'<i class="fa fa-moon"></i>':'<i class="fa fa-sun"></i>';
  try{ localStorage.setItem('funlearn_theme', dark?'light':'dark'); }catch(e){}
});
// Restore saved theme
try{
  const t=localStorage.getItem('funlearn_theme');
  if(t){ document.documentElement.setAttribute('data-theme',t);
         document.getElementById('theme-toggle').innerHTML=t==='dark'?'<i class="fa fa-sun"></i>':'<i class="fa fa-moon"></i>'; }
}catch(e){}

/* ── MODULE: ALPHABETS ─────────────────────────────────────── */
(function buildAlphaGrid(){
  const grid=document.getElementById('alpha-grid');
  ALPHABETS.forEach((a,i)=>{
    const btn=document.createElement('button');
    btn.className='alpha-tile';
    btn.textContent=a.l;
    btn.style.background=TILE_COLORS[i%TILE_COLORS.length];
    btn.setAttribute('aria-label',`Letter ${a.l}`);
    btn.addEventListener('click',()=>{ sfx.pop(); showAlpha(i); });
    grid.appendChild(btn);
  });
})();

function showAlpha(idx){
  state.alphaIdx=idx;
  const a=ALPHABETS[idx];
  document.getElementById('alpha-cap').textContent   = a.l;
  document.getElementById('alpha-small').textContent = a.l.toLowerCase();
  document.getElementById('alpha-word').textContent  = `${a.l} for ${a.w}`;
  document.getElementById('alpha-hindi').textContent = a.hi;
  const img=document.getElementById('alpha-img');
  img.src=a.img; img.alt=a.w;
  document.querySelectorAll('.alpha-tile').forEach((t,i)=>t.classList.toggle('selected',i===idx));
  // Re-trigger card animation
  const card=document.getElementById('alpha-card');
  card.style.animation='none'; requestAnimationFrame(()=>{ card.style.animation=''; });
  speak(`${a.l}. ${a.l} for ${a.w}.`);
  trackItem('alpha', idx);
}

document.getElementById('alpha-prev').addEventListener('click',()=>{ sfx.click(); showAlpha((state.alphaIdx-1+26)%26); });
document.getElementById('alpha-next').addEventListener('click',()=>{ sfx.click(); showAlpha((state.alphaIdx+1)%26); });
document.getElementById('alpha-speak').addEventListener('click',()=>{ sfx.click(); speak(ALPHABETS[state.alphaIdx].w); });

function toggleSlideshow(){
  const btn=document.getElementById('alpha-slide-toggle');
  state.slideRunning=!state.slideRunning;
  if (state.slideRunning){
    btn.innerHTML='<i class="fa fa-pause"></i> Pause'; btn.style.background='#FF6B6B';
    state.slideInterval=setInterval(()=>showAlpha((state.alphaIdx+1)%26), 2800);
  } else {
    btn.innerHTML='<i class="fa fa-play"></i> Slideshow'; btn.style.background='';
    clearInterval(state.slideInterval);
  }
}
document.getElementById('alpha-slide-toggle').addEventListener('click',()=>{ sfx.click(); toggleSlideshow(); });
showAlpha(0);

/* ── MODULE: NUMBERS ───────────────────────────────────────── */
(function buildNumGrid(){
  const grid=document.getElementById('num-grid');
  NUMBERS.forEach((n,i)=>{
    const btn=document.createElement('button');
    btn.className='num-tile';
    btn.style.background=TILE_COLORS[i%TILE_COLORS.length];
    btn.innerHTML=`${n.n}<br><small>${n.w}</small>`;
    btn.setAttribute('aria-label',`Number ${n.n}, ${n.w}`);
    btn.addEventListener('click',()=>{ sfx.pop(); showNumber(i); });
    grid.appendChild(btn);
  });
})();

function showNumber(idx){
  const n=NUMBERS[idx];
  document.getElementById('num-grid').style.display='none';
  const card=document.getElementById('num-card'); card.style.display='flex';
  document.getElementById('num-big-num').textContent=n.n;
  document.getElementById('num-word').textContent=n.w;
  const wrap=document.getElementById('emoji-count-wrap'); wrap.innerHTML='';
  for(let i=0;i<n.n;i++){
    const s=document.createElement('span'); s.className='count-emoji'; s.textContent=n.e;
    s.style.animationDelay=`${i*0.07}s`; s.title=`${i+1}`;
    s.addEventListener('click',()=>{ sfx.pop(); speak(String(i+1)); });
    wrap.appendChild(s);
  }
  card.style.animation='none'; requestAnimationFrame(()=>{ card.style.animation=''; });
  speak(n.w); trackItem('num',idx);
}

document.getElementById('num-back').addEventListener('click',()=>{
  sfx.click();
  document.getElementById('num-card').style.display='none';
  document.getElementById('num-grid').style.display='grid';
});
document.getElementById('num-speak').addEventListener('click',()=>{
  sfx.click();
  const n=parseInt(document.getElementById('num-big-num').textContent)-1;
  speak(`${NUMBERS[n].n}. ${NUMBERS[n].w}.`);
});

/* ── MODULE: COLORS ────────────────────────────────────────── */
(function buildColors(){
  const grid=document.getElementById('color-grid');
  COLORS.forEach((c,i)=>{
    const card=document.createElement('div');
    card.className='color-card';
    card.setAttribute('role','button'); card.setAttribute('tabindex','0');
    card.setAttribute('aria-label',`${c.name} color`);
    card.innerHTML=`<div class="color-swatch" style="background:${c.hex}"></div>
      <div class="color-info"><div class="color-name">${c.name}</div><div class="color-example">${c.example}</div></div>`;
    const act=()=>{ sfx.pop(); speak(c.name); trackItem('color',i); };
    card.addEventListener('click',act);
    card.addEventListener('keydown',e=>{ if(e.key==='Enter'||e.key===' ')act(); });
    grid.appendChild(card);
  });
})();

/* ── MODULE: SHAPES ────────────────────────────────────────── */
(function buildShapes(){
  const grid=document.getElementById('shape-grid');
  SHAPES.forEach(s=>{
    const card=document.createElement('div');
    card.className='shape-card';
    card.setAttribute('role','button'); card.setAttribute('tabindex','0');
    card.setAttribute('aria-label',`${s.name} shape`);
    card.innerHTML=`${s.svg}<div class="shape-name">${s.name}</div><div class="shape-desc">${s.desc}</div>`;
    const act=()=>{ sfx.pop(); speak(`${s.name}. ${s.desc}`); };
    card.addEventListener('click',act);
    card.addEventListener('keydown',e=>{ if(e.key==='Enter'||e.key===' ')act(); });
    grid.appendChild(card);
  });
})();

// Color/Shape tabs
document.querySelectorAll('.tab-btn').forEach(btn=>{
  btn.addEventListener('click',()=>{
    sfx.click();
    document.querySelectorAll('.tab-btn').forEach(b=>b.classList.remove('active'));
    document.querySelectorAll('.tab-content').forEach(t=>t.classList.remove('active'));
    btn.classList.add('active'); document.getElementById(btn.dataset.tab).classList.add('active');
  });
});

/* ── MODULE: RHYMES ────────────────────────────────────────── */
(function buildRhymeTabs(){
  const wrap=document.getElementById('rhyme-tabs');
  RHYMES.forEach((r,i)=>{
    const btn=document.createElement('button');
    btn.className='rhyme-tab-btn'+(i===0?' active':'');
    btn.textContent=`${r.art} ${r.title}`;
    btn.addEventListener('click',()=>{
      sfx.click(); stopRhyme();
      document.querySelectorAll('.rhyme-tab-btn').forEach(b=>b.classList.remove('active'));
      btn.classList.add('active'); state.rhymeIdx=i; renderRhyme(i);
    });
    wrap.appendChild(btn);
  });
  renderRhyme(0);
})();

function renderRhyme(idx){
  const r=RHYMES[idx];
  document.getElementById('rhyme-art').textContent=r.art;
  document.getElementById('rhyme-title').textContent=r.title;
  const box=document.getElementById('lyrics-box');
  box.innerHTML=r.lines.map((l,i)=>`<div class="lyric-line" id="lyric-${i}">${l}</div>`).join('');
}

function playRhyme(){
  const r=RHYMES[state.rhymeIdx];
  document.getElementById('rhyme-art').classList.add('playing');
  speak(r.lines.join(' ... '), 0.75, 1.1);
  let lineIdx=0; clearTimeout(state.rhymeTimer);
  function next(){
    document.querySelectorAll('.lyric-line').forEach(l=>l.classList.remove('active'));
    const el=document.getElementById(`lyric-${lineIdx}`);
    if(el){ el.classList.add('active'); el.scrollIntoView({block:'nearest',behavior:'smooth'}); }
    lineIdx++;
    if(lineIdx<r.lines.length) state.rhymeTimer=setTimeout(next,1900);
    else state.rhymeTimer=setTimeout(stopRhyme,1900);
  }
  next();
}

function stopRhyme(){
  if(window.speechSynthesis) window.speechSynthesis.cancel();
  clearTimeout(state.rhymeTimer);
  document.getElementById('rhyme-art').classList.remove('playing');
  document.querySelectorAll('.lyric-line').forEach(l=>l.classList.remove('active'));
}

document.getElementById('rhyme-play').addEventListener('click',()=>{ sfx.click(); playRhyme(); });
document.getElementById('rhyme-stop').addEventListener('click',()=>{ sfx.click(); stopRhyme(); });

/* ── MODULE: QUIZ ──────────────────────────────────────────── */

/** Utility: Fisher-Yates shuffle */
function shuffle(arr){ const a=[...arr]; for(let i=a.length-1;i>0;i--){ const j=Math.floor(Math.random()*(i+1)); [a[i],a[j]]=[a[j],a[i]]; } return a; }

/** Build 4 unique answer options (1 correct + 3 distractors) */
function buildOptions(correct, pool){
  const wrong=shuffle(pool.filter(x=>x!==correct)).slice(0,3);
  return shuffle([correct,...wrong]);
}

/** Assemble question objects for a given category */
function buildQuestions(cat){
  let qs=[];
  const limit = cat==='mixed'?3:10;

  if(cat==='alphabets'||cat==='mixed'){
    shuffle([...ALPHABETS]).slice(0,limit).forEach(a=>{
      qs.push({text:`"${a.l}" is for...?`, img:a.img, swatch:null, svg:null,
               answer:a.w, options:buildOptions(a.w, ALPHABETS.map(x=>x.w))});
    });
  }
  if(cat==='numbers'||cat==='mixed'){
    shuffle([...NUMBERS]).slice(0,limit).forEach(n=>{
      const disp=n.n<=8?n.e.repeat(n.n):`${n.e} × ${n.n}`;
      qs.push({text:`How many? ${disp}`, img:null, swatch:null, svg:null,
               answer:n.w, options:buildOptions(n.w, NUMBERS.map(x=>x.w))});
    });
  }
  if(cat==='colors'||cat==='mixed'){
    shuffle([...COLORS]).slice(0,cat==='mixed'?2:10).forEach(c=>{
      qs.push({text:'What color is this?', img:null, swatch:c.hex, svg:null,
               answer:c.name, options:buildOptions(c.name, COLORS.map(x=>x.name))});
    });
  }
  if(cat==='shapes'||cat==='mixed'){
    shuffle([...SHAPES]).slice(0,cat==='mixed'?2:8).forEach(s=>{
      qs.push({text:'What shape is this?', img:null, swatch:null, svg:s.svg,
               answer:s.name, options:buildOptions(s.name, SHAPES.map(x=>x.name))});
    });
  }
  return shuffle(qs).slice(0,10);
}

function startQuiz(cat){
  state.quiz={ category:cat, questions:buildQuestions(cat), current:0, score:0, answered:false };
  document.getElementById('quiz-intro').style.display='none';
  document.getElementById('quiz-result').style.display='none';
  document.getElementById('quiz-active').style.display='block';
  renderQuestion();
}

function renderQuestion(){
  const {questions,current,score}=state.quiz;
  if(current>=questions.length){ showResult(); return; }
  const q=questions[current];
  state.quiz.answered=false;

  document.getElementById('quiz-q-num').textContent=`Q ${current+1} / ${questions.length}`;
  document.getElementById('quiz-score-live').textContent=score;
  document.getElementById('quiz-q-text').textContent=q.text;
  document.getElementById('quiz-feedback').textContent='';
  document.getElementById('quiz-feedback').className='quiz-feedback';

  // Question visual
  const iw=document.getElementById('quiz-q-img-wrap'); iw.innerHTML='';
  if(q.img){
    iw.innerHTML=`<img src="${q.img}" alt="${q.answer}" width="160" height="140" loading="lazy">`;
  } else if(q.swatch){
    iw.innerHTML=`<div style="width:120px;height:90px;background:${q.swatch};border-radius:16px;margin:0 auto;box-shadow:0 4px 14px rgba(0,0,0,.18)"></div>`;
  } else if(q.svg){
    const tmp=document.createElement('div'); tmp.innerHTML=q.svg;
    const svgEl=tmp.querySelector('svg');
    svgEl.style.cssText='width:100px;height:100px;margin:0 auto;display:block';
    iw.appendChild(svgEl);
  }

  // Options
  const ow=document.getElementById('quiz-options'); ow.innerHTML='';
  q.options.forEach(opt=>{
    const btn=document.createElement('button');
    btn.className='quiz-opt'; btn.textContent=opt;
    btn.addEventListener('click',()=>checkAnswer(opt,btn));
    ow.appendChild(btn);
  });
}

function checkAnswer(chosen, clickedBtn){
  if(state.quiz.answered) return;
  state.quiz.answered=true;
  const correct=state.quiz.questions[state.quiz.current].answer;
  const fb=document.getElementById('quiz-feedback');

  if(chosen===correct){
    state.quiz.score++;
    clickedBtn.classList.add('correct');
    fb.textContent='✅ Correct! Well done! 🎉';
    fb.className='quiz-feedback correct-fb';
    sfx.correct();
  } else {
    clickedBtn.classList.add('wrong');
    document.querySelectorAll('.quiz-opt').forEach(b=>{ if(b.textContent===correct) b.classList.add('correct'); });
    fb.textContent=`❌ Oops! The answer is "${correct}"`;
    fb.className='quiz-feedback wrong-fb';
    sfx.wrong();
  }
  document.getElementById('quiz-score-live').textContent=state.quiz.score;
  state.quiz.current++;
  setTimeout(()=>{ if(state.quiz.current<state.quiz.questions.length) renderQuestion(); else showResult(); }, 1500);
}

function showResult(){
  document.getElementById('quiz-active').style.display='none';
  document.getElementById('quiz-result').style.display='block';
  const {score,questions}=state.quiz;
  const pct=Math.round((score/questions.length)*100);
  if(pct>progress.quizBest){ progress.quizBest=pct; saveProgress(); }

  let msg,badge;
  if(pct>=90){msg='🎉 Excellent!';badge='⭐⭐⭐';}
  else if(pct>=60){msg='👍 Good Job!';badge='⭐⭐';}
  else{msg='💪 Try Again!';badge='⭐';}

  document.getElementById('result-badge').textContent=badge;
  document.getElementById('result-msg').textContent=msg;
  document.getElementById('result-score-text').textContent=`You scored ${score} out of ${questions.length}! (${pct}%)`;

  if(pct>=60) launchConfetti();
  speak(msg.replace(/[^a-zA-Z ]/g,''));
  updateProgressUI();
}

document.querySelectorAll('.quiz-cat-btn').forEach(btn=>{
  btn.addEventListener('click',()=>{ sfx.click(); startQuiz(btn.dataset.cat); });
});
document.getElementById('quiz-restart').addEventListener('click',()=>{
  sfx.click();
  document.getElementById('quiz-result').style.display='none';
  document.getElementById('quiz-intro').style.display='block';
});
document.getElementById('quiz-home').addEventListener('click',()=>{
  sfx.click();
  document.getElementById('quiz-result').style.display='none';
  document.getElementById('quiz-intro').style.display='block';
  navigate('home');
});

/* ── CONFETTI ANIMATION ────────────────────────────────────── */
function launchConfetti(){
  const canvas=document.getElementById('confetti-canvas');
  canvas.style.display='block'; canvas.width=innerWidth; canvas.height=innerHeight;
  const ctx=canvas.getContext('2d');
  const pieces=Array.from({length:140},()=>({
    x:Math.random()*canvas.width, y:Math.random()*-canvas.height*0.5,
    w:8+Math.random()*10, h:5+Math.random()*7,
    col:TILE_COLORS[Math.floor(Math.random()*TILE_COLORS.length)],
    vx:-2.5+Math.random()*5, vy:2.5+Math.random()*5,
    vr:-5+Math.random()*10, rot:Math.random()*360,
  }));
  let frame=0;
  (function draw(){
    ctx.clearRect(0,0,canvas.width,canvas.height);
    pieces.forEach(p=>{
      ctx.save(); ctx.translate(p.x+p.w/2, p.y+p.h/2); ctx.rotate((p.rot*Math.PI)/180);
      ctx.fillStyle=p.col;
      if(Math.round(p.vx*10)%2===0){ ctx.fillRect(-p.w/2,-p.h/2,p.w,p.h); }
      else { ctx.beginPath(); ctx.arc(0,0,p.w/2,0,Math.PI*2); ctx.fill(); }
      ctx.restore();
      p.x+=p.vx; p.y+=p.vy; p.rot+=p.vr;
    });
    frame++;
    if(frame<200) requestAnimationFrame(draw);
    else { canvas.style.display='none'; ctx.clearRect(0,0,canvas.width,canvas.height); }
  })();
}

/* ── INIT ─────────────────────────────────────────────────── */
updateProgressUI();