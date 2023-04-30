// npm init
// npm run build-css
/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["*"],
  theme: {
    extend: {
      fontFamily: {
        'title' : ['Sansita', 'sans-serif'],
        'body' :  ['Lato', 'sans-serif']
      },
      colors: {
        'pri' : '#081F4D',
        'sec' : '#0083FF',
        'gen' : '#3B4D71',
        'new-bg-color' : '#D9E6FF',
      }
    },
  },
  plugins: [],
}
