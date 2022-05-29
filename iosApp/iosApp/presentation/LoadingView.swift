//
//  LoadingView.swift
//  iosApp
//
//  Created by Aliyu Olalekan on 29/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct LoadingView: View {
    
    @State private var isAnimating: Bool = false
    
    var body: some View {
        GeometryReader { (geometry: GeometryProxy) in
            ForEach(0..<5) { index in
                Group {
                    Circle()
                        .frame(width: geometry.size.width / 8, height: geometry.size.height / 8)
                        .scaleEffect(calcScale(index: index))
                        .offset(y: calcYOffset(geometry))
                        .foregroundColor(.orange)
                }.frame(width: geometry.size.width, height: geometry.size.height)
                    .rotationEffect(!self.isAnimating ? .degrees(0) : .degrees(360))
                    .animation(Animation
                        .timingCurve(0.5, 0.15 + Double(index) / 5, 0.25, 1, duration: 1.5)
                        .repeatForever(autoreverses: false))
            }
        }
        .aspectRatio(1, contentMode: .fit)
        .onAppear {
            self.isAnimating = true
        }
    }
    
    func calcScale(index: Int) -> CGFloat {
        return (!isAnimating ? 1 - CGFloat(Float(index)) / 5 : 0.2 + CGFloat(index) / 5)
    }
    
    func calcYOffset(_ geometry: GeometryProxy) -> CGFloat {
        return geometry.size.width / 10 - geometry.size.height / 2
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView()
    }
}
