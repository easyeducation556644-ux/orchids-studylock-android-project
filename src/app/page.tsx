"use client";

import Image from "next/image";

export default function Home() {
  return (
    <div className="flex min-h-screen items-center justify-center bg-zinc-50 font-sans dark:bg-black">
      <main className="flex min-h-screen w-full max-w-3xl flex-col items-center justify-center py-32 px-16 bg-white dark:bg-black sm:items-start text-center sm:text-left">
        <div className="mb-12">
          <h1 className="text-4xl font-bold tracking-tight text-black dark:text-zinc-50 mb-4">
            StudyLock Project Ready
          </h1>
          <p className="text-xl text-zinc-600 dark:text-zinc-400">
            The Gradle wrapper has been added and the build errors have been fixed.
          </p>
        </div>

        <div className="grid gap-8 w-full">
          <section className="p-6 rounded-2xl bg-zinc-50 dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800">
            <h2 className="text-lg font-semibold mb-3 flex items-center gap-2">
              <span className="flex h-2 w-2 rounded-full bg-green-500"></span>
              Latest Updates
            </h2>
            <ul className="space-y-2 text-zinc-600 dark:text-zinc-400">
              <li>• Gradle wrapper files added to <code>/StudyLock</code></li>
              <li>• <code>gradlew.bat</code> fixed for JAVA_HOME paths with spaces</li>
              <li>• Java 17 compatibility configured for JDK 25</li>
              <li>• Changes committed and pushed to Orchids storage</li>
            </ul>
          </section>

          <section className="p-6 rounded-2xl bg-zinc-50 dark:bg-zinc-900 border border-zinc-200 dark:border-zinc-800">
            <h2 className="text-lg font-semibold mb-3">Next Steps</h2>
            <div className="space-y-4">
              <div className="flex flex-col gap-2">
                <p className="text-sm font-medium">To build the APK locally:</p>
                <code className="bg-black text-white p-3 rounded-lg text-sm">
                  cd StudyLock && ./gradlew assembleDebug
                </code>
              </div>
              <p className="text-sm text-zinc-500 italic">
                Note: If you don't see changes on GitHub, make sure to sync your project from the Orchids dashboard.
              </p>
            </div>
          </section>
        </div>

        <div className="mt-12 flex flex-col gap-4 sm:flex-row">
          <a
            className="flex h-12 items-center justify-center rounded-full bg-black text-white px-8 font-medium transition-transform active:scale-95"
            href="https://github.com"
            target="_blank"
            rel="noopener noreferrer"
          >
            Check GitHub
          </a>
          <button
            onClick={() => window.parent.postMessage({ type: "OPEN_EXTERNAL_URL", data: { url: "https://orchids.dev" } }, "*")}
            className="flex h-12 items-center justify-center rounded-full border border-zinc-200 px-8 font-medium transition-colors hover:bg-zinc-50"
          >
            Orchids Dashboard
          </button>
        </div>
      </main>
    </div>
  );
}
