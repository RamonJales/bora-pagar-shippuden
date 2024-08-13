/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        "bp_primary": {
          400: "#42A1FF", // Main Color
          50: "#E3F1FF",
          100: "#BBDCFF",
          200: "#90C7FF",
          300: "#63B1FF",
          500: "#2790FE",
          600: "#2A82EF",
          700: "#2970DB",
          800: "#285EC8",
          900: "#1F3286"
        },
        "bp_neutral": {
          900: "#1A1A1A", // Main color
          50: "#F7F7F7",
          100: "#EEEEEE",
          200: "#E2E2E2",
          300: "#D0D0D0",
          400: "#ABABAB",
          500: "#8A8A8A",
          600: "#636363",
          700: "#505050",
          800: "#323232"
        },
        "bp_danger": "#DA1E28",
        "bp_sucess": "#6AE156",
        "bp_yellow": "#FFCC00"
      }
    },
  },
  plugins: [],
}

