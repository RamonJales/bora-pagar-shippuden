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
          900: "#030303", // Main color
          50: "#F7F7F7",
          100: "#E6E6E6",
          200: "#D9D9D9",
          300: "#BFBFBF",
          400: "#999999",
          500: "#737373",
          600: "#505050",
          700: "#323232",
          800: "#1A1A1A"
        },

        "bp_danger": {
          DEFAULT: "#DA1E28",
          light: "#FFAAAA"
        },

        "bp_sucess": "#6AE156",
        "bp_yellow": "#FFCC00"
      }
      },
    },
  plugins: [],
}