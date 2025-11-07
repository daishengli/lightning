// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: "2025-07-15",
  devtools: { enabled: false },
  app: {
    head: {
      title: "Lightning",
      meta: [
        {
          name: "description",
          content:
            "Lightning is a fast, modern, and powerful web framework for building web applications.",
        },
      ],
    },
  }
});
