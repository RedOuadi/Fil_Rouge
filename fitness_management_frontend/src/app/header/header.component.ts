import { Component } from "@angular/core";

@Component({
  selector: "app-header",
  template: `
    <header class="flex flex-col items-center">
      <div class="flex flex-col justify-center items-center px-11 bg-white max-w-[1263px] min-h-[100px] w-[1263px] max-md:px-5 max-md:max-w-full">
        <div class="flex flex-wrap flex-1 gap-5 items-center py-7 size-full">
          <div class="flex flex-col justify-center self-stretch my-auto max-w-[1173px] w-[120px]">
            <div class="flex flex-col flex-1 pt-1 w-full">
              <div class="flex items-center w-full">
                <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/f670f004cce1dfc7a3e6104a928214aad5197753771622b293acc9509a222812?placeholderIfAbsent=true&apiKey=ed70e4763e93400387347c7c359d052b" alt="Company logo" class="object-contain self-stretch my-auto aspect-[4.81] max-w-[120px] w-[120px]" />
              </div>
            </div>
          </div>
          <nav class="flex flex-col flex-1 shrink justify-center self-stretch pt-2 my-auto basis-0 max-w-[1173px] min-w-[240px] max-md:max-w-full">
            <div class="flex flex-col flex-1 w-full max-md:max-w-full">
              <div class="flex flex-col items-center pr-28 pb-2 pl-28 w-full max-md:px-5 max-md:max-w-full">
                <ul class="flex flex-wrap justify-center items-start max-md:max-w-full">
                  <li class="flex relative flex-col px-9 w-[113px] max-md:px-5">
                    <a href="#" class="z-0 self-center text-sm font-extrabold leading-7 text-center text-neutral-900">Home</a>
                    <div class="flex absolute top-0 bottom-px z-0 flex-col w-3 h-7 left-[83px] right-[18px]">
                      <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/7cb3eda31e004c9899e5bf38e51824bf3ad135f54be9be6946e23affc511e815?placeholderIfAbsent=true&apiKey=ed70e4763e93400387347c7c359d052b" alt="" class="object-contain w-full aspect-[0.44]" />
                    </div>
                  </li>
                  <li class="flex relative flex-col pr-9 pl-9 w-[135px] max-md:px-5">
                    <a href="#" class="z-0 self-center text-sm font-extrabold leading-7 text-center text-neutral-900">About Us</a>
                    <div class="flex absolute top-0 bottom-px z-0 flex-col w-3 h-7 left-[104px] right-[18px]">
                      <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/4b5f5b64d6127a8cd21e947dfbbc42ff92eead2dfc57bdad01e005886a342b65?placeholderIfAbsent=true&apiKey=ed70e4763e93400387347c7c359d052b" alt="" class="object-contain w-full aspect-[0.44]" />
                    </div>
                  </li>
                  <li class="flex relative flex-col px-9 w-[135px] max-md:px-5">
                    <a href="#" class="z-0 self-center text-sm font-extrabold leading-7 text-center text-neutral-900">Products</a>
                    <div class="flex absolute top-0 bottom-px z-0 flex-col w-3 h-7 left-[106px] right-[18px]">
                      <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/5ae3b65232c434378e486128195be8ec319238d9668456e2692d289d7a3967eb?placeholderIfAbsent=true&apiKey=ed70e4763e93400387347c7c359d052b" alt="" class="object-contain w-full aspect-[0.44]" />
                    </div>
                  </li>
                  <li class="flex relative flex-col pr-9 pl-9 w-[103px] max-md:px-5">
                    <a href="#" class="z-0 self-center text-sm font-extrabold leading-7 text-center text-neutral-900">Blog</a>
                    <div class="flex absolute top-0 bottom-px z-0 flex-col w-3 h-7 left-[73px] right-[18px]">
                      <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/3bc1409cdef7a0549e72d108331145adb6ff53036d2b1ae8d8d41a9b4b59751c?placeholderIfAbsent=true&apiKey=ed70e4763e93400387347c7c359d052b" alt="" class="object-contain w-full aspect-[0.44]" />
                    </div>
                  </li>
                  <li class="px-9 text-sm font-extrabold leading-7 text-center whitespace-nowrap text-neutral-900 w-[136px] max-md:px-5">
                    <a href="#">Contacts</a>
                  </li>
                </ul>
              </div>
            </div>
          </nav>
          <div class="flex flex-col justify-center self-stretch my-auto max-w-[1173px] w-[162px]">
            <div class="flex flex-col flex-1 w-full">
              <div class="flex flex-col w-full">
                <div class="flex w-full">
                  <button class="flex flex-col justify-center self-start px-5 py-1.5 min-h-[28px]" aria-label="Search">
                    <div class="flex items-start w-[18px]">
                      <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/381dcf04b2f6d0d44691528278a2f20a5ed0639e6fc90eec7c1920ff09e7473b?placeholderIfAbsent=true&apiKey=ed70e4763e93400387347c7c359d052b" alt="" class="object-contain aspect-square w-[18px]" />
                    </div>
                  </button>
                  <button class="flex flex-col justify-center px-4 py-1 min-h-[28px]" aria-label="Cart">
                    <div class="flex relative flex-col pt-1 pr-9 pb-0.5 w-full max-md:pr-5">
                      <div class="flex z-0 items-start w-full">
                        <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/7e81caeab0d453cd26aeb621a296e963ad1740b9fc7c29345e435c1b991a3f9a?placeholderIfAbsent=true&apiKey=ed70e4763e93400387347c7c359d052b" alt="" class="object-contain aspect-square w-[18px]" />
                      </div>
                      <span class="absolute -top-1 right-4 px-1.5 w-5 h-5 text-xs leading-5 text-center text-white whitespace-nowrap bg-orange-600 rounded-xl min-h-[20px]">
                        0
                      </span>
                    </div>
                  </button>
                  <button class="flex flex-col min-h-[28px]" aria-label="User profile">
                    <div class="flex justify-center items-start w-[18px]">
                      <div class="flex flex-col items-center pt-1.5 pb-1 w-[18px]">
                        <div class="flex items-start w-full">
                          <img loading="lazy" src="https://cdn.builder.io/api/v1/image/assets/TEMP/281054efaa34fdee2faf4478d029faf700db401c29a806cf04470fc9b18fb4fe?placeholderIfAbsent=true&apiKey=ed70e4763e93400387347c7c359d052b" alt="" class="object-contain aspect-square w-[18px]" />
                        </div>
                      </div>
                    </div>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>
  `,
  styles: [
    `
      :host {
        display: contents;
      }
    `,
  ],
})
export class HeaderComponent {}
